package cn.edu.gxust.blogex.common.enums;

import cn.edu.gxust.blogex.common.annotation.EnumCode;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhaoyijie
 * @since 2022/5/15 13:05
 */
public enum MailEnableEnum {

    ON("ON", 0),
    OFF("OFF", 1);

    @EnumCode
    private final int code;

    //@JsonValue
    private final String desc;

    private static final Map<Integer, MailEnableEnum> ENUM_MAP = Arrays.stream(MailEnableEnum.values())
            .collect(Collectors.toMap(MailEnableEnum::getCode, Function.identity()));

    MailEnableEnum(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static MailEnableEnum of(int code) {
        return ENUM_MAP.get(code);
    }

    @Override
    public String toString() {
        return "MailEnableEnum{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
