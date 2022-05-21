package cn.edu.gxust.blogex.common.exception;

/**
 * @author zhaoyijie
 * @since 2022/4/3 13:04
 */
public class UploadException extends RuntimeException {

    public UploadException() {
        super();
    }

    public UploadException(Throwable cause) {
        super(cause);
    }

    public UploadException(String message) {
        super(message);
    }


}
