package cn.edu.gxust.blogex.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 同com.baomidou.mybatisplus.annotation.EnumValue，用于标识枚举值
 * 与cn.edu.gxust.blogex.common.annotation.EnumValid一起使用
 * 具体校验方式请看cn.edu.gxust.blogex.common.valid
 *
 * @author zhaoyijie
 * @since 2022/3/14 10:00
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EnumCode {

}
