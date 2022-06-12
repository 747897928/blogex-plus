package cn.edu.gxust.blogex.common.enums;

import cn.edu.gxust.blogex.common.annotation.EnumCode;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhaoyijie
 * @since 2022/6/11 21:16
 */
public enum EnabledEnum {

    OFF("关闭", 0),
    ON("开启", 1);

    @EnumCode
    private final int code;

    private final String desc;

    private static final Map<Integer, EnabledEnum> ENUM_MAP = Arrays.stream(EnabledEnum.values())
            .collect(Collectors.toMap(EnabledEnum::getCode, Function.identity()));

    EnabledEnum(String desc, int code) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static EnabledEnum of(int code) {
        return ENUM_MAP.get(code);
    }

    @Override
    public String toString() {
        return "EnabledEnum{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
