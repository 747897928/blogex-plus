package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.convertor.VerifyCodeConvertor;
import cn.edu.gxust.blogex.api.dto.VerifyCodeDTO;
import cn.edu.gxust.blogex.api.service.VerifyCodeService;
import cn.edu.gxust.blogex.api.vo.VerifyCodeVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.entity.VerifyCode;
import cn.edu.gxust.blogex.common.exception.ValidateCodeException;
import cn.edu.gxust.blogex.common.utils.RandomUtils;
import cn.edu.gxust.blogex.common.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现类
 *
 * @author zhaoyijie
 * @since 2022/3/24 09:53
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    private final static Logger logger = LoggerFactory.getLogger(VerifyCodeServiceImpl.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final int VALICATE_CODE_LENGTH = 4;

    @Override
    public VerifyCodeVO getVerifyCode() {
        String randomStr = RandomUtils.randomString(VALICATE_CODE_LENGTH);
        return getVerifyCode(randomStr);
    }

    @Override
    public VerifyCodeVO getVerifyCode(String randomStr) {
        try {
            VerifyCode imageVerifyCode = VerifyCodeUtils.getImageVerifyCode(randomStr);
            VerifyCodeVO verifyCode = VerifyCodeConvertor.convert(imageVerifyCode);
            int timestamp = 5 * 60 * 1000;/*五分钟过期*/
            logger.info(verifyCode.toString());
            String uuid = verifyCode.getUuid();
            /*缓存VerifyCode*/
            String cacheKey = Constants.VCODE_CACHE_KEY_PREFIX + uuid;
            stringRedisTemplate.opsForValue().set(cacheKey, verifyCode.getCode(), timestamp, TimeUnit.MILLISECONDS);
            /*返回之前清空code值*/
            verifyCode.setCode(null);
            return verifyCode;
        } catch (Exception e) {
            throw new ValidateCodeException("生成验证码时出现了错误：" + e.getMessage());
        }
    }

    @Override
    public void verifyCode(VerifyCodeDTO verifyCodeDTO) {
        String sourceCode = verifyCodeDTO.getCode();
        String uuid = verifyCodeDTO.getCodeUuid();
        String cacheKey = Constants.VCODE_CACHE_KEY_PREFIX + uuid;
        String cacheCode = stringRedisTemplate.opsForValue().get(cacheKey);
        VerifyCodeUtils.verifyImageCode(sourceCode, cacheCode);
    }

}
