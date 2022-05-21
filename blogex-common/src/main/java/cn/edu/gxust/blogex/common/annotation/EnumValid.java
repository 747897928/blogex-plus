package cn.edu.gxust.blogex.common.annotation;

import cn.edu.gxust.blogex.common.valid.EnumIntValidator;
import cn.edu.gxust.blogex.common.valid.EnumStringValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 配合@Validate一起使用的、用来校验枚举的注解
 *
 * @author zhaoyijie
 * @since 2022/3/13 21:12
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumIntValidator.class, EnumStringValidator.class})
public @interface EnumValid {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> target();

}
