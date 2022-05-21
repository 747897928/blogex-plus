package cn.edu.gxust.blogex.common.enums;

import cn.edu.gxust.blogex.common.annotation.EnumCode;

/**
 *
 * @author zhaoyijie
 * @since 2021/7/23 18:15
 */
public enum PostType {

    ORIGINAL("原创", 0),
    REPRINT("转载", 1),
    INTERPRET("翻译", 2);

    @EnumCode
    private final int code;

    //@JsonValue
    private final String desc;

    PostType(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "PostType{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
