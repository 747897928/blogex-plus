package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.convertor.VerificationConvertor;
import cn.edu.gxust.blogex.api.convertor.VerifyCodeConvertor;
import cn.edu.gxust.blogex.api.dto.VerifyCodeDTO;
import cn.edu.gxust.blogex.api.entity.VerifyCodeMap;
import cn.edu.gxust.blogex.api.service.VerifyCodeService;
import cn.edu.gxust.blogex.api.vo.VerificationVO;
import cn.edu.gxust.blogex.api.vo.VerifyCodeVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.entity.Verification;
import cn.edu.gxust.blogex.common.entity.VerifyCode;
import cn.edu.gxust.blogex.common.enums.VerifyCodeTypeEnum;
import cn.edu.gxust.blogex.common.exception.ValidateCodeException;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import cn.edu.gxust.blogex.common.utils.RandomUtils;
import cn.edu.gxust.blogex.common.utils.StringUtils;
import cn.edu.gxust.blogex.common.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
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

    @Override
    public VerificationVO sliderVerificationCode() {
        try {
            Verification verification = VerifyCodeUtils.sliderVerificationCode();
            VerifyCodeMap verifyCodeMap = VerifyCodeMap.newBuilder()
                    .verifyCodeType(VerifyCodeTypeEnum.SLIDER.getCode())
                    .verifyCodeData(verification.getxWidth().toString()).build();
            String jsonData = JSONUtils.toJsonString(verifyCodeMap);
            String codeUuid = verification.getCodeUuid();
            stringRedisTemplate.opsForValue().set(Constants.Login_VCODE_CACHE_KEY + Constants.COLON + codeUuid,
                    jsonData, 5 * 60, TimeUnit.SECONDS);
            VerificationVO verificationVO = VerificationConvertor.convert(verification);
            /*移除横坐标送前端*/
            verificationVO.setxWidth(null);
            verificationVO.setCodeUuid(codeUuid);
            return verificationVO;
        } catch (Exception e) {
            logger.error("Captcha interception failed {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public VerifyCodeVO getLoginImageVerifyCode() {
        try {
            VerifyCode imageVerifyCode = VerifyCodeUtils.getImageVerifyCode();
            VerifyCodeVO verifyCode = VerifyCodeConvertor.convert(imageVerifyCode);
            int timestamp = 5 * 60 * 1000;/*五分钟过期*/
            logger.info(verifyCode.toString());
            /*缓存VerifyCode*/
            String codeUuid = verifyCode.getUuid();
            VerifyCodeMap verifyCodeMap = VerifyCodeMap.newBuilder()
                    .verifyCodeType(VerifyCodeTypeEnum.IMAGE.getCode())
                    .verifyCodeData(verifyCode.getCode()).build();
            String jsonData = JSONUtils.toJsonString(verifyCodeMap);
            String cacheKey = Constants.Login_VCODE_CACHE_KEY + Constants.COLON + codeUuid;
            stringRedisTemplate.opsForValue().set(cacheKey, jsonData, timestamp, TimeUnit.MILLISECONDS);
            /*返回之前清空code值*/
            verifyCode.setCode(null);
            return verifyCode;
        } catch (Exception e) {
            throw new ValidateCodeException("生成验证码时出现了错误：" + e.getMessage());
        }
    }

    /**
     * 校验登录验证证码的有效性
     *
     */
    public void validateLoginCode(String codeUuid, String verifyCode) {
        if (StringUtils.isEmpty(verifyCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        String verifyCodeStr = stringRedisTemplate.opsForValue().get(Constants.Login_VCODE_CACHE_KEY + Constants.COLON + codeUuid);
        if (verifyCodeStr == null) {
            throw new ValidateCodeException("验证码已经过期或不存在");
        }
        VerifyCodeMap verifyCodeMap = JSONUtils.parseObject(verifyCodeStr, VerifyCodeMap.class);
        assert verifyCodeMap != null;
        Integer verifyCodeType = verifyCodeMap.getVerifyCodeType();
        String verifyCodeData = verifyCodeMap.getVerifyCodeData();
        VerifyCodeTypeEnum verifyCodeTypeEnum = VerifyCodeTypeEnum.of(verifyCodeType);
        switch (Objects.requireNonNull(verifyCodeTypeEnum)) {
            case IMAGE:
                VerifyCodeUtils.verifyImageCode(verifyCode, verifyCodeData);
                break;
            case SLIDER:
                VerifyCodeUtils.verifySliderCode(verifyCode, verifyCodeData);
                break;
        }

    }

}
