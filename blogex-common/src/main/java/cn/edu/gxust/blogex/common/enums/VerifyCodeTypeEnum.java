package cn.edu.gxust.blogex.common.enums;

import cn.edu.gxust.blogex.common.annotation.EnumCode;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhaoyijie
 * @since 2022/4/27 20:55
 */
public enum VerifyCodeTypeEnum {

    SLIDER("滑块验证码", 0),
    IMAGE("图片验证码", 1);

    @EnumCode
    private final Integer code;

    //@JsonValue
    private final String desc;

    private static final Map<Integer, VerifyCodeTypeEnum> VERIFY_CODE_TYPE_MAP = Arrays.stream(VerifyCodeTypeEnum.values())
            .collect(Collectors.toMap(VerifyCodeTypeEnum::getCode, Function.identity()));

    VerifyCodeTypeEnum(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static VerifyCodeTypeEnum of(int code) {
        return VERIFY_CODE_TYPE_MAP.get(code);
    }

    @Override
    public String toString() {
        return "VerifyCodeType{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
