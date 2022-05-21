package cn.edu.gxust.blogex.common.enums;


public enum ErrorCode {

    /**
     * 错误码
     */
    MOVED_TEMPORARILY(302, "token非法/需要重新登录认证"),
    CUSTOM_ERROR(400, "客户端错误"),
    UNAUTHENTICATED(401, "未登录/未认证"),
    AUTHENTICATE_ERROR(402, "认证失败"),
    UNAUTHORIZED(403, "无权限"),
    PARAM_VALUE_ERROR(411, "参数数值错误"),
    PARAM_TYPE_ERROR(412, "参数类型错误"),
    PARAM_MISSING_ERROR(413, "缺少必要参数"),

    SERVER_ERROR(500, "服务端错误"),
    REMOTE_SERVER_ERROR(501, "远程服务器错误"),
    TIME_OUT(502, "请求超时"),
    EXISTED_ERROR(503, "操作内容已存在"),
    NOT_EXISTED_ERROR(504, "操作内容不存在"),
    SQL_ERROR(510, "数据库错误/数据库操作失败"),
    CREATE_ERROR(511, "新增失败"),
    DELETE_ERROR(512, "删除失败"),
    QUERY_ERROR(513, "查询失败"),
    UPDATE_ERROR(514, "更新失败");

    private final int code;

    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

}
