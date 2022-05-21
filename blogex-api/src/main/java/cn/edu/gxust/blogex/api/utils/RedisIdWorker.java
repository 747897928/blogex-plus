package cn.edu.gxust.blogex.api.utils;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Redis实现的全局唯一id
 *
 * @author zhaoyijie
 * @since 2022/4/4 20:55
 */
public class RedisIdWorker {

    /**
     * 开始时间戳2022-01-01 08:00:00
     */
    private static final long BEGIN_TIMESTAMP = 1640995200L;

    /**
     * 序列号位数
     */
    private static final int COUNT_BITS = 32;

    private final StringRedisTemplate stringRedisTemplate;

    public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public long nextId(String keyPrefix) {
        //1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;

        //2.生成序列号
        //2.1获取当前日期，精确到天
        String formatDate = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        //2.2自增长
        long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + formatDate);

        //3.拼接返回
        //符号位   时间戳31bit                           序列号（32bit）
        //0   -   0000000 00000000 00000000 00000000 - 00000000 00000000 00000000 00000000
        //用或运算填充序列号，参考LeetCode 260. 只出现一次的数字 III
        return timestamp << COUNT_BITS | count;
    }

}
