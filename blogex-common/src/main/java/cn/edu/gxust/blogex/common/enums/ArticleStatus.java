package cn.edu.gxust.blogex.common.enums;

import cn.edu.gxust.blogex.common.annotation.EnumCode;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author zhaoyijie
 * @since 2021/7/23 18:03
 */
public enum ArticleStatus {

    PUBLISH("公开", 0),
    DRAFT("草稿", 1);

    @EnumCode
    private final Integer code;

    //@JsonValue
    private final String desc;

    private static final Map<Integer, ArticleStatus> ARTICLE_STATUS_MAP = Arrays.stream(ArticleStatus.values())
            .collect(Collectors.toMap(ArticleStatus::getCode, Function.identity()));

    ArticleStatus(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ArticleStatus of(int code) {
        return ARTICLE_STATUS_MAP.get(code);
    }

    @Override
    public String toString() {
        return "ArticleStatus{" +
                "desc='" + desc + '\'' +
                ", code=" + code +
                '}';
    }
}
