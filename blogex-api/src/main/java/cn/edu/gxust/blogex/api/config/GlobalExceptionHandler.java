package cn.edu.gxust.blogex.api.config;

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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public Result<?> exceptionHandler(Exception e) {
        logger.error("未知异常", e);
        return Result.error(ErrorCode.SERVER_ERROR.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BlogException.class)
    public ResponseEntity<?> blogExceptionHandler(BlogException e) {
        logger.error("Blog异常: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(Result.error(e.getCode(), e.getMessage()));
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        logger.error("客户端错误: {}", e.getMessage());
        return Result.error(ErrorCode.CUSTOM_ERROR.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = ResourcesNotFoundException.class)
    public Result<?> resourcesNotFoundExceptionHandler(ResourcesNotFoundException e) {
        logger.error("资源找不到: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = OperationNotAllowedException.class)
    public Result<?> operationNotAllowedExceptionHandler(OperationNotAllowedException e) {
        logger.error("操作不允许: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = javax.validation.ConstraintViolationException.class)
    public Result<?> constraintViolationExceptionHandler(javax.validation.ConstraintViolationException e) {
        logger.error("客户端异常: {}", e.getMessage());
        return Result.error(ErrorCode.CUSTOM_ERROR.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = org.springframework.web.bind.MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidExceptionHandler(
            org.springframework.web.bind.MethodArgumentNotValidException e) {
        logger.error("请求参数不完整: {}", e.getMessage());
        List<ObjectError> objectErrorList = e.getBindingResult().getAllErrors();
        List<String> errorMsgList = objectErrorList.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String messages = StringUtils.join(errorMsgList, ";");
        return Result.error(ErrorCode.CUSTOM_ERROR.getCode(), messages);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = cn.edu.gxust.blogex.common.exception.ValidateCodeException.class)
    public Result<?> validateCodeExceptionHandler(cn.edu.gxust.blogex.common.exception.ValidateCodeException e) {
        logger.error("校验验证码不通过: {}", e.getMessage());
        return Result.error(ErrorCode.CUSTOM_ERROR.getCode(), e.getMessage());
    }

}
