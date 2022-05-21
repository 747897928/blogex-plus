package cn.edu.gxust.blogex.gateway.handler;

import cn.edu.gxust.blogex.common.enums.ErrorCode;
import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 处理访问没有经过身份验证
 *
 * @author zhaoyijie
 */
@Component
public class CustomHttpBasicServerAuthenticationEntryPoint extends HttpBasicServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        Result<Object> result = Result.error(ErrorCode.UNAUTHENTICATED.getCode(), "your access is not authenticated");
        byte[] dataBytes = JSONUtils.toJsonByteArray(result);
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
        return response.writeWith(Mono.just(bodyDataBuffer));

    }
}
