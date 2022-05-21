package cn.edu.gxust.blogex.api.config;

import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
@Order(1)
public class WebLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * cn.edu.gxust.blogex.api.controller.*.*(..))")
    public void weblog() {
    }

    @Before("weblog()")
    public void doBefore(JoinPoint joinPoint) {
        //获取请求报文头部元数据
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求对象
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            //log.info("前置通知执行：");
            String url = request.getRequestURL().toString();
            String method = request.getMethod();
            String args = Arrays.toString(joinPoint.getArgs());
            String aspectLogDescription = getAspectLogDescription(joinPoint);
            logger.info("date = {},url = {},method = {},args = {} ,aspectLogDescription = {}", new Date(), url, method, args, aspectLogDescription);
        }
    }

    /**
     * 获取切面注解ApiOperation的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     */
    public String getAspectLogDescription(JoinPoint joinPoint) {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        String targetName = targetClass.getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == arguments.length) {
                    ApiOperation annotation = method.getAnnotation(ApiOperation.class);
                    if (annotation != null) {
                        description.append(annotation.value());
                    }
                    break;
                }
            }
        }
        return description.toString();
    }

}