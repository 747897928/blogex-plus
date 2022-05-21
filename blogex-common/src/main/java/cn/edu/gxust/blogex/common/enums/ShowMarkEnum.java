package cn.edu.gxust.blogex.common.enums;

import cn.edu.gxust.blogex.common.annotation.EnumCode;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhaoyijie
 * @since 2022/4/30 17:20
 */
public enum ShowMarkEnum {

    HIDE("隐藏", 0),
    SHOW("展示", 1);

    @EnumCode
    private final Integer code;

    private final String desc;

    private static final Map<Integer, ShowMarkEnum> SHOW_MARK_ENUM_MAP = Arrays.stream(ShowMarkEnum.values())
            .collect(Collectors.toMap(ShowMarkEnum::getCode, Function.identity()));

    ShowMarkEnum(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ShowMarkEnum of(int code) {
        return SHOW_MARK_ENUM_MAP.get(code);
    }

    @Override
    public String toString() {
        return "ShowMarkEnum{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
