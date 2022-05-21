package cn.edu.gxust.blogex.gateway.handler;

import cn.edu.gxust.blogex.common.enums.ErrorCode;
import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 处理未授权
 *
 * @author zhaoyijie
 * @since 2022/3/21 16:52
 */
@Component
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        //设置body
        byte[] dataBytes = JSONUtils.toJsonByteArray(Result.error(ErrorCode.UNAUTHORIZED.getCode(), "your operation is not authorized"));
        DataBuffer bodyDateBuffer = response.bufferFactory().wrap(dataBytes);
        return response.writeWith(Mono.just(bodyDateBuffer));
    }

}
