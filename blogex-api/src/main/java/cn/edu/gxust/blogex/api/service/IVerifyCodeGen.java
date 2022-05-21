package cn.edu.gxust.blogex.api.service;


import cn.edu.gxust.blogex.api.vo.VerifyCodeVO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 验证码生成接口
 */
public interface IVerifyCodeGen {

    /**
     * 生成验证码并返回code，将图片写的os中
     */
    String generate(int width, int height, String randomStr,OutputStream os) throws IOException;

    /**
     * 生成验证码对象
     */
    VerifyCodeVO generate(int width, int height);

    /**
     * 生成验证码对象
     */
    VerifyCodeVO generate(int width, int height, String randomStr);

}
