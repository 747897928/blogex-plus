package cn.edu.gxust.blogex.common.valid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cn.edu.gxust.blogex.common.annotation.EnumCode;
import cn.edu.gxust.blogex.common.annotation.EnumValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoyijie
 * @since 2022/3/13 21:14
 */

public class EnumStringValidator implements ConstraintValidator<EnumValid, String> {

    private static final Logger logger = LoggerFactory.getLogger(ConstraintValidator.class);

    /**
     * 枚举值列表
     */
    private final List<String> enumCodeValueList = new ArrayList<>();

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        Class<?> targetClass = constraintAnnotation.target();
        Field codeField = null;
        if (targetClass.isEnum()) {
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isEnumConstant()) {
                    continue;
                }
                EnumCode enumCode = field.getAnnotation(EnumCode.class);
                if (null != enumCode) {
                    codeField = field;
                    break;
                }
            }
            if (null == codeField) {
                throw new NullPointerException("The @EnumCode annotation is not found in the field of the enumeration class");
            }
            Object[] objs = targetClass.getEnumConstants();
            codeField.setAccessible(true);
            for (Object obj : objs) {
                try {
                    enumCodeValueList.add((String) codeField.get(obj));
                } catch (Exception e) {
                    logger.error("获取枚举类值时发生错误：", e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return false;
        }
        for (String enumCodeValue : enumCodeValueList) {
            if (value.equals(enumCodeValue)) {
                return true;
            }
        }
        return false;
    }

}
