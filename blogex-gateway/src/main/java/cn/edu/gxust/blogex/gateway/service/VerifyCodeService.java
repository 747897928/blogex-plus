package cn.edu.gxust.blogex.gateway.service;

import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.entity.Verification;
import cn.edu.gxust.blogex.common.entity.VerifyCode;
import cn.edu.gxust.blogex.common.enums.VerifyCodeTypeEnum;
import cn.edu.gxust.blogex.common.exception.ValidateCodeException;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import cn.edu.gxust.blogex.common.utils.RandomUtils;
import cn.edu.gxust.blogex.common.utils.StringUtils;
import cn.edu.gxust.blogex.common.utils.VerifyCodeUtils;
import cn.edu.gxust.blogex.gateway.convertor.VerificationConvertor;
import cn.edu.gxust.blogex.gateway.convertor.VerifyCodeConvertor;
import cn.edu.gxust.blogex.gateway.entity.VerifyCodeMap;
import cn.edu.gxust.blogex.gateway.utils.VerifyImageUtils;
import cn.edu.gxust.blogex.gateway.vo.VerificationVO;
import cn.edu.gxust.blogex.gateway.vo.VerifyCodeVO;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaoyijie
 * @since 2022/3/23 19:23
 */
@Service
public class VerifyCodeService {

    private final static Logger logger = LoggerFactory.getLogger(VerifyCodeService.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;

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

    /**
     * 校验滑动验证码的有效性
     *
     * @param verifyCode 滑动验证码
     */
    public void validate(String codeUuid, String verifyCode) {
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


    public VerifyCodeVO getImageVerifyCode() {
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
}
