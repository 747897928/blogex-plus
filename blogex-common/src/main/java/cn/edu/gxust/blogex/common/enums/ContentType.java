package cn.edu.gxust.blogex.common.enums;

import cn.edu.gxust.blogex.common.annotation.EnumCode;
//import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <p>description:  </p>
 * <p>create: 2021/7/23 17:35</p>
 *
 * @author zhaoyijie
 * @version v1.0
 */
public enum ContentType {

    HTML("HTML", 0),
    MARKDOWN("MARKDOWN", 1);

    @EnumCode
    private final int code;

    //@JsonValue
    private final String desc;

    ContentType(String desc, int code) {
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
        return "ContentType{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
