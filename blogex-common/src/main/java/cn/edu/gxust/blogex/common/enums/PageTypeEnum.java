package cn.edu.gxust.blogex.common.enums;

import cn.edu.gxust.blogex.common.annotation.EnumCode;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhaoyijie
 * @since 2022/4/17 10:38
 */
public enum PageTypeEnum {

    ARTICLE_COMMENT("文章评论", 0),
    ABOUT_ME_COMMENT("关于我评论", 1),
    LINK_COMMENT("友联评论", 2);

    @EnumCode
    private final Integer code;

    //@JsonValue
    private final String desc;

    private static final Map<Integer, PageTypeEnum> PAGE_TYPE_ENUM_MAP = Arrays.stream(PageTypeEnum.values())
            .collect(Collectors.toMap(PageTypeEnum::getCode, Function.identity()));

    PageTypeEnum(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PageTypeEnum of(int code) {
        return PAGE_TYPE_ENUM_MAP.get(code);
    }

    @Override
    public String toString() {
        return "PageTypeEnum{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
