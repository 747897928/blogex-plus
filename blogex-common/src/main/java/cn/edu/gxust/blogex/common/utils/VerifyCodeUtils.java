package cn.edu.gxust.blogex.common.utils;

import cn.edu.gxust.blogex.common.entity.Verification;
import cn.edu.gxust.blogex.common.entity.VerifyCode;
import cn.edu.gxust.blogex.common.exception.ValidateCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 图片验证码工具类整合，滑块验证和干扰线图片验证
 * VerifySliderImageUtils和IVerifyCodeGenUtils的整合，对外提供统一的入口
 *
 * @author zhaoyijie
 * @since 2022/5/22 20:43
 */
public class VerifyCodeUtils {

    private final static Logger logger = LoggerFactory.getLogger(VerifyCodeUtils.class);

    private static final int VALICATE_CODE_LENGTH = 4;

    /**
     * 获取滑块验证码
     */
    public static Verification sliderVerificationCode() {
        try {
            // 读取图库目录
            File imgCatalog = getClassPathFile("static/images/slider/targets");
            File[] listFiles = imgCatalog.listFiles();
            if (null != listFiles) {
                if (listFiles.length > 0) {
                    // 随机选择需要切的图
                    int randNum = new Random().nextInt(listFiles.length);
                    File targetFile = listFiles[randNum];
                    // 随机选择剪切模版
                    File tempImgFile = getClassPathFile("static/images/slider/templates/" + (new Random().nextInt(6) + 1) + "-w.png");
                    Verification verification = VerifySliderImageUtils.pictureTemplatesCut(tempImgFile, targetFile);
                    verification.setExpireTime(new Date().getTime() + 5 * 60 * 1000);/*五分钟过期*/
                    String codeUuid = UUID.randomUUID().toString();
                    verification.setCodeUuid(codeUuid);
                    logger.info(verification.toString());
                    return verification;
                }
            }
            throw new RuntimeException("读取滑块验证码图库目录出现错误");
        } catch (Exception e) {
            logger.error("Captcha interception failed {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取干扰线图片验证码
     *
     * @param randomStr 指定图片上显示的字符串
     */
    public static VerifyCode getImageVerifyCode(String randomStr) {
        try {
            /*设置长宽*/
            VerifyCode verifyCode = IVerifyCodeGenUtils.generate(80, 28, randomStr);
            int timestamp = 5 * 60 * 1000;/*五分钟过期*/
            /*这步操作只是让前端看到过期时间，本质上不会用到这个字段*/
            verifyCode.setExpireTime(new Date().getTime() + timestamp);
            /*缓存VerifyCode*/
            String codeUuid = UUID.randomUUID().toString();
            verifyCode.setUuid(codeUuid);
            logger.info(verifyCode.toString());
            return verifyCode;
        } catch (Exception e) {
            throw new ValidateCodeException("生成验证码时出现了错误：" + e.getMessage());
        }
    }

    /**
     * 获取干扰线图片验证码
     */
    public static VerifyCode getImageVerifyCode() {
        String randomStr = RandomUtils.randomString(VALICATE_CODE_LENGTH);
        return getImageVerifyCode(randomStr);
    }

    /**
     * 验证干扰线图片验证码的有效性
     */
    public static void verifyImageCode(String sourceCode, String verifyCodeStr) {
        if (verifyCodeStr == null) {
            throw new ValidateCodeException("验证码不存在或者已经过期");
        }
        if (!verifyCodeStr.equalsIgnoreCase(sourceCode)) {
            throw new ValidateCodeException("验证码不匹配");
        }
    }

    /**
     * 验证滑块验证码的有效性
     */
    public static void verifySliderCode(String sourceCode, String verifyCodeStr) {
        double vCode = Double.parseDouble(sourceCode);
        int xWidth = Integer.parseInt(verifyCodeStr.trim());
        if (xWidth - vCode > 5 || xWidth - vCode < -5) {
            throw new ValidateCodeException("验证码不匹配");
        }
    }

    public static File getClassPathFile(String filePath) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        URL url = classPathResource.getURL();
        logger.debug(url.toString());
        String protocol = url.getProtocol();
        logger.debug(protocol);
        if ("file".equals(protocol)) {
            String path = url.getPath();
            logger.debug("path:{},not jar", path);
            return classPathResource.getFile();
        } else if ("jar".equals(protocol)) {
            logger.debug("{} is jar", url.getPath());
            /*如果是jar包内的文件，只能用流的方式读取，但是这个是文件夹，
            所以在父pom中，将静态文件打出jar外。这时候只需要读取jar外的资源文件即可*/
            File file = new File(filePath);
            logger.debug("file.getAbsoluteFile():{}", file.getAbsoluteFile());
            return file;
        }
        return null;
    }

}
