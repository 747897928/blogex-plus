package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.service.CountService;
import cn.edu.gxust.blogex.api.vo.CountVO;
import cn.edu.gxust.blogex.api.vo.EchartsVO;
import cn.edu.gxust.blogex.common.utils.DateUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhaoyijie
 * @since 2022/4/8 17:10
 */
@Service
public class CountServiceImpl implements CountService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final static String COUNT_KEY_PREFIX = "count";

    @Override
    public CountVO count() {
        Date nowDate = new Date();

        //today
        String todayFormatDate = DateUtils.formatDateColon().format(nowDate);
        String todayValueStr = stringRedisTemplate.opsForValue().get("icr:" + COUNT_KEY_PREFIX + ":" + todayFormatDate);
        int todayCount = todayValueStr == null ? 0 : Integer.parseInt(todayValueStr);

        //week
        int weekCount = 0;
        List<String> weekColonList = DateUtils.getThisWeekColonList(nowDate);
        List<Integer> seriesData = new ArrayList<>(Collections.nCopies(7, 0));
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < weekColonList.size(); i++) {
            String weekColon = weekColonList.get(i);
            String weekKey = "icr:" + COUNT_KEY_PREFIX + ":" + weekColon;
            map.put(weekKey, i);
        }

        //mouth
        int mouthCount = 0;
        String mouthCountFormatDate = new SimpleDateFormat("yyyy:MM").format(nowDate);
        Set<String> mouthKeys = stringRedisTemplate.keys("icr:" + COUNT_KEY_PREFIX + ":" + mouthCountFormatDate + "*");
        for (String mouthKey : mouthKeys) {
            int dayCount = Integer.parseInt(stringRedisTemplate.opsForValue().get(mouthKey));
            mouthCount = mouthCount + dayCount;
            Integer integer = map.get(mouthKey);
            if (null != integer) {
                seriesData.set(integer, dayCount);
            }
        }

        //total
        String totalCountStr = stringRedisTemplate.opsForValue().get("icr:" + COUNT_KEY_PREFIX);
        long totalCount = totalCountStr == null ? 0L : Long.parseLong(totalCountStr);

        EchartsVO<String, Integer> echartsVO = new EchartsVO<>(weekColonList, seriesData);
        return CountVO.newBuilder().todayCount(todayCount).weekCount(weekCount).mouthCount(mouthCount)
                .totalCount(totalCount).echartsData(echartsVO).build();
    }

    @Override
    public long increase() {
        LocalDateTime now = LocalDateTime.now();
        String formatDate = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        //出于Redis对于事务的支持并不是很完善，事务中遇到错误不会回滚，不建议启用redis事务，如果这里使用会抛出空指针异常
        //Redis跟关系型数据库不太一样，开启事务支持以后，会自动执行multi命令，接下来所有的命令都会进入一个队列Queue中等待，
        // 直到调用exec命令时才会真正顺序去执行，所以代码中拿不到返回值。
        //total+1
        stringRedisTemplate.opsForValue().increment("icr:" + COUNT_KEY_PREFIX);
        //today+1
        return stringRedisTemplate.opsForValue().increment("icr:" + COUNT_KEY_PREFIX + ":" + formatDate);
    }
}
