package cn.edu.gxust.blogex.common.exception;

/**
 * @author zhaoyijie
 * @since 2022/3/29 09:22
 */
public class InvalidFileFormatException extends RuntimeException{

    public InvalidFileFormatException(String message) {
        super(message);
    }
}
