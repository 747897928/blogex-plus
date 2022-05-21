package cn.edu.gxust.blogex.gateway.service;

import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.enums.VerifyCodeTypeEnum;
import cn.edu.gxust.blogex.common.exception.ValidateCodeException;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import cn.edu.gxust.blogex.common.utils.RandomUtils;
import cn.edu.gxust.blogex.common.utils.StringUtils;
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

    private static final String[] FONT_TYPES = {"\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66"};

    private static final int VALICATE_CODE_LENGTH = 4;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public VerificationVO sliderVerificationCode() {
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
                    VerificationVO verificationVO = VerifyImageUtils.pictureTemplatesCut(tempImgFile, targetFile);
                    verificationVO.setExpireTime(new Date().getTime() + 5 * 60 * 1000);/*五分钟过期*/
                    String codeUuid = UUID.randomUUID().toString();
                    VerifyCodeMap verifyCodeMap = VerifyCodeMap.newBuilder()
                            .verifyCodeType(VerifyCodeTypeEnum.SLIDER.getCode())
                            .verifyCodeData(verificationVO.getxWidth().toString()).build();
                    String jsonData = JSONUtils.toJsonString(verifyCodeMap);
                    stringRedisTemplate.opsForValue().set(Constants.Login_VCODE_CACHE_KEY + Constants.COLON + codeUuid,
                            jsonData, 5 * 60, TimeUnit.SECONDS);
                    /*移除横坐标送前端*/
                    verificationVO.setxWidth(null);
                    verificationVO.setCodeUuid(codeUuid);
                    return verificationVO;
                }
            }
        } catch (Exception e) {
            logger.error("Captcha interception failed {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
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
                verifyImageCode(verifyCode, verifyCodeData);
                break;
            case SLIDER:
                verifySliderCode(verifyCode, verifyCodeData);
                break;
        }

    }

    public File getClassPathFile(String filePath) throws IOException {
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

    /**
     * 验证图片验证码
     */
    public void verifySliderCode(String sourceCode, String verifyCodeStr) {
        double vCode = Double.parseDouble(sourceCode);
        int xWidth = Integer.parseInt(verifyCodeStr.trim());
        if (xWidth - vCode > 5 || xWidth - vCode < -5) {
            throw new ValidateCodeException("验证码不匹配");
        }
    }


    public VerifyCodeVO getImageVerifyCode() {
        try {
            /*设置长宽*/
            VerifyCodeVO verifyCode = generate(80, 28);
            int timestamp = 5 * 60 * 1000;/*五分钟过期*/
            /*这步操作只是让前端看到过期时间，本质上不会用到这个字段*/
            verifyCode.setExpireTime(new Date().getTime() + timestamp);
            logger.info(verifyCode.toString());
            /*缓存VerifyCode*/
            String codeUuid = UUID.randomUUID().toString();
            verifyCode.setUuid(codeUuid);
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
     * 验证图片验证码
     */
    public void verifyImageCode(String sourceCode, String verifyCodeStr) {
        if (verifyCodeStr == null) {
            throw new ValidateCodeException("验证码不存在或者已经过期");
        }
        if (!verifyCodeStr.equalsIgnoreCase(sourceCode)) {
            throw new ValidateCodeException("验证码不匹配");
        }
    }

    /**
     * 设置背景颜色及大小，干扰线
     */
    private static void fillBackground(Graphics graphics, int width, int height) {
        // 填充背景
        graphics.setColor(Color.WHITE);
        //设置矩形坐标x y 为0
        graphics.fillRect(0, 0, width, height);

        // 加入干扰线条
        for (int i = 0; i < 8; i++) {
            //设置随机颜色算法参数
            graphics.setColor(RandomUtils.randomColor(40, 150));
            Random random = new Random();
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.drawLine(x, y, x1, y1);
        }
    }

    /**
     * 验证码生成
     */
    public VerifyCodeVO generate(int width, int height) {
        VerifyCodeVO verifyCode = null;
        try (
                //将流的初始化放到这里就不需要手动关闭流
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            String code = generate(width, height, baos);
            verifyCode = new VerifyCodeVO();
            verifyCode.setCode(code);
            verifyCode.setBase64ImgStr(new Base64().encodeToString(baos.toByteArray()));
        } catch (IOException e) {
            verifyCode = null;
        }
        return verifyCode;
    }

    /**
     * 生成随机字符
     */
    public String generate(int width, int height, OutputStream os) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        fillBackground(graphics, width, height);
        String randomStr = RandomUtils.randomString(VALICATE_CODE_LENGTH);
        createCharacter(graphics, randomStr);
        graphics.dispose();
        //设置JPEG格式
        ImageIO.write(image, "JPEG", os);
        return randomStr;
    }

    /**
     * 设置字符颜色大小
     */
    private void createCharacter(Graphics g, String randomStr) {
        char[] charArray = randomStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            //设置RGB颜色算法参数
            g.setColor(new Color(50 + RandomUtils.nextInt(100),
                    50 + RandomUtils.nextInt(100), 50 + RandomUtils.nextInt(100)));
            //设置字体大小，类型
            g.setFont(new Font(FONT_TYPES[RandomUtils.nextInt(FONT_TYPES.length)], Font.BOLD, 26));
            //设置x y 坐标
            g.drawString(String.valueOf(charArray[i]), 15 * i + 5, 19 + RandomUtils.nextInt(8));
        }
    }
}
