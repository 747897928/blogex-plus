package cn.edu.gxust.blogex.common.utils;

import cn.edu.gxust.blogex.common.entity.VerifyCode;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 干扰线图片验证码工具类
 *
 * @author zhaoyijie
 * @since 2022/5/22 20:04
 */
public class IVerifyCodeGenUtils {

    private static final String[] FONT_TYPES = {"\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66"};

    private static final int VALICATE_CODE_LENGTH = 4;

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
    public static VerifyCode generate(int width, int height) {
        String randomStr = RandomUtils.randomString(VALICATE_CODE_LENGTH);
        return generate(width, height, randomStr);
    }


    public static VerifyCode generate(int width, int height, String randomStr) {
        VerifyCode verifyCode = null;
        try (
                //将流的初始化放到这里就不需要手动关闭流
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            String code = generate(width, height, randomStr, baos);
            verifyCode = new VerifyCode();
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
    public static String generate(int width, int height, String randomStr, OutputStream os) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        fillBackground(graphics, width, height);
        createCharacter(graphics, randomStr);
        graphics.dispose();
        //设置JPEG格式
        ImageIO.write(image, "JPEG", os);
        return randomStr;
    }

    /**
     * 设置字符颜色大小
     */
    private static void createCharacter(Graphics g, String randomStr) {
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