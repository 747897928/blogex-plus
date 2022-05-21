package cn.edu.gxust.blogex.gateway.config;

import cn.edu.gxust.blogex.common.enums.ErrorCode;
import cn.edu.gxust.blogex.common.exception.BlogException;
import cn.edu.gxust.blogex.common.exception.OperationNotAllowedException;
import cn.edu.gxust.blogex.common.exception.ResourcesNotFoundException;
import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaoyijie
 * @since 2022/4/1 10:36
 */
@Component
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final static String HANDLE_FUN_NAME = "handle";

    public Object handle(Throwable e, ServerHttpResponse response) {
        try {
            Class<? extends Throwable> errorClass = e.getClass();
            Method method = getClass().getMethod(HANDLE_FUN_NAME, errorClass, ServerHttpResponse.class);
            return method.invoke(this, e, response);
        } catch (Exception ex) {
            logger.error("未知异常", e);
            response.setStatusCode(HttpStatus.OK);
            return Result.error(ErrorCode.SERVER_ERROR.getCode(), e.getMessage());
        }
    }

    public Result<String> handle(ResponseStatusException ex, ServerHttpResponse response) {
        HttpStatus httpStatus = ex.getStatus();
        response.setStatusCode(HttpStatus.OK);
        return Result.error(httpStatus.value(), ex.getMessage());
    }

    public Result<String> handle(BlogException e, ServerHttpResponse response) {
        logger.error("Blog异常: {}", e.getMessage());
        response.setStatusCode(HttpStatus.OK);
        return Result.error(e.getCode(), e.getMessage());
    }

    public Result<String> handle(IllegalArgumentException e, ServerHttpResponse response) {
        logger.error("客户端错误: {}", e.getMessage());
        response.setStatusCode(HttpStatus.OK);
        return Result.error(ErrorCode.CUSTOM_ERROR.getCode(), e.getMessage());
    }

    public Result<String> handle(ResourcesNotFoundException e, ServerHttpResponse response) {
        logger.error("资源找不到: {}", e.getMessage());
        response.setStatusCode(HttpStatus.OK);
        return Result.error(e.getCode(), e.getMessage());
    }

    public Result<String> handle(OperationNotAllowedException e, ServerHttpResponse response) {
        logger.error("操作不允许: {}", e.getMessage());
        response.setStatusCode(HttpStatus.OK);
        return Result.error(e.getCode(), e.getMessage());
    }

    public Result<String> handle(javax.validation.ConstraintViolationException e, ServerHttpResponse response) {
        logger.error("客户端异常: {}", e.getMessage());
        response.setStatusCode(HttpStatus.OK);
        return Result.error(ErrorCode.CUSTOM_ERROR.getCode(), e.getMessage());
    }

    public Result<String> handle(org.springframework.web.bind.MethodArgumentNotValidException e, ServerHttpResponse response) {
        logger.error("请求参数不完整: {}", e.getMessage());
        response.setStatusCode(HttpStatus.OK);
        List<ObjectError> objectErrorList = e.getBindingResult().getAllErrors();
        List<String> errorMsgList = objectErrorList.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String messages = StringUtils.join(errorMsgList, ";");
        return Result.error(ErrorCode.CUSTOM_ERROR.getCode(), messages);
    }

    public Result<String> handle(org.springframework.web.bind.support.WebExchangeBindException e, ServerHttpResponse response) {
        logger.error("请求参数不完整: {}", e.getMessage());
        response.setStatusCode(HttpStatus.OK);
        List<ObjectError> objectErrorList = e.getBindingResult().getAllErrors();
        List<String> errorMsgList = objectErrorList.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String messages = StringUtils.join(errorMsgList, ";");
        return Result.error(ErrorCode.CUSTOM_ERROR.getCode(), messages);
    }

    public Result<String> handle(cn.edu.gxust.blogex.common.exception.ValidateCodeException e, ServerHttpResponse response) {
        logger.error("校验验证码不通过: {}", e.getMessage());
        response.setStatusCode(HttpStatus.OK);
        return Result.error(ErrorCode.CUSTOM_ERROR.getCode(), e.getMessage());
    }

    public Result<String> handle(BadCredentialsException e, ServerHttpResponse response) {
        logger.error("权限认证不通过: {}", e.getMessage());
        response.setStatusCode(HttpStatus.OK);
        return Result.error(ErrorCode.AUTHENTICATE_ERROR.getCode(), e.getMessage() + ",the permission authentication fails");
    }

}
