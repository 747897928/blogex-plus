package cn.edu.gxust.blogex.gateway.filter;

import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.gateway.utils.NetworkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 获取请求用户的ip，将ip地址设置到请求头上，供下游服务使用
 *
 * @author zhaoyijie
 * @since 2022/3/26 12:08
 */
@Component
public class UserIPGlobalFilter implements GlobalFilter, Ordered {

    private final static Logger logger = LoggerFactory.getLogger(UserIPGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //获取ip，设置下游请求的头信息
        String ipAddress = NetworkUtils.getIPAddress(request);
        logger.info("request ipAddress = {}", ipAddress);
        ServerHttpRequest newRequest = request.mutate().headers(httpHeaders -> {
            httpHeaders.remove(Constants.X_USER_IP);
            httpHeaders.add(Constants.X_USER_IP, ipAddress);
        }).build();
        ServerWebExchange build = exchange.mutate().request(newRequest).build();
        return chain.filter(build);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
