package cn.edu.gxust.blogex.common.response;

import cn.edu.gxust.blogex.common.enums.ErrorCode;

import java.util.List;

/**
 * 通用返回体
 */
public class Result<T> {

    public static final int SUCCESS_CODE = 200;

    public static final String SUCCESS_MESSAGE = "success";

    private Integer code;

    private String message;

    private T data;

    public Result() {
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(SUCCESS_CODE, message, data);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(ErrorCode.SERVER_ERROR.getCode(), message);
    }

    public static <T> Result<T> error(String message, T data) {
        return new Result<>(ErrorCode.SERVER_ERROR.getCode(), message, data);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message);
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMessage());
    }

    public static <T> Result<Pagination<T>> pagination(Pagination<T> pagination) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, pagination);
    }

    public static <T> Result<Pagination<T>> pagination(Long pageNo, Long pageSize, List<T> list) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, new Pagination<>(pageNo, pageSize, list));
    }

    public static <T> Result<Pagination<T>> pagination(Long pageNo, Long pageSize, Long total, List<T> list) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, new Pagination<>(pageNo, pageSize, total, list));
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
