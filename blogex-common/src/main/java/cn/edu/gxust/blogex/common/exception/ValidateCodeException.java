package cn.edu.gxust.blogex.common.exception;

/**
 * @author zhaoyijie
 * @since 2022/3/24 09:30
 */
public class ValidateCodeException extends RuntimeException{

    public ValidateCodeException() {
        this("验证码校验不通过");
    }

    public ValidateCodeException(String message) {
        super(message);
    }

}
