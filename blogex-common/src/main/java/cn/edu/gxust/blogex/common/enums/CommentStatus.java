package cn.edu.gxust.blogex.common.enums;

import cn.edu.gxust.blogex.common.annotation.EnumCode;
//import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author zhaoyijie
 * @since 2021/7/23 18:03
 */
public enum CommentStatus {

    OPEN_AUTO("开启评论并自动处理", 0),
    CLOSE("不开启评论", 1);

    @EnumCode
    //@JsonValue
    private final int code;

    private final String desc;

    private static final Map<Integer, CommentStatus> COMMENT_STATUS_MAP = Arrays.stream(CommentStatus.values())
            .collect(Collectors.toMap(CommentStatus::getCode, Function.identity()));

    CommentStatus(String desc, int code) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static CommentStatus of(int code) {
        return COMMENT_STATUS_MAP.get(code);
    }

    @Override
    public String toString() {
        return "CommentStatus{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
