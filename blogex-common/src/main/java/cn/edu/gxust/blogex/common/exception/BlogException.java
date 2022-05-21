package cn.edu.gxust.blogex.common.exception;

import cn.edu.gxust.blogex.common.enums.ErrorCode;

public class BlogException extends RuntimeException {

    private final int code;

    private final String message;

    public BlogException(String message) {
        super(message);
        this.code = 400;
        this.message = message;
    }

    public BlogException(Throwable cause) {
        super(cause);
        this.code = 400;
        this.message = cause.getMessage();
    }

    public BlogException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BlogException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
