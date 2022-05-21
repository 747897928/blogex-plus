package cn.edu.gxust.blogex.gateway.filter;

import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.enums.ErrorCode;
import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import cn.edu.gxust.blogex.gateway.entity.BlogUser;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理token的校验
 *
 * @author zhaoyijie
 * @since 2022/3/22 15:11
 */
public class CustomTokenVerifyFilter implements WebFilter {

    private final StringRedisTemplate stringRedisTemplate;

    public CustomTokenVerifyFilter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        HttpHeaders httpHeaders = request.getHeaders();
        String token = httpHeaders.getFirst(Constants.AUTHORIZATION);
        if (null != token) {
            String cacheKey = Constants.USER_CACHE_PREFIX_KEY + token;
            String blogUserJson = stringRedisTemplate.opsForValue().get(cacheKey);
            if (null != blogUserJson) {
                Authentication authentication = createAuthentication(blogUserJson);
                return webFilterChain.filter(serverWebExchange).subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication));
            }else {
                ServerHttpResponse response = serverWebExchange.getResponse();
                Result<String> result = Result.error(ErrorCode.MOVED_TEMPORARILY);
                return response.writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = response.bufferFactory();
                    return bufferFactory.wrap(JSONUtils.toJsonByteArray(result));
                }));
            }
        }
        return webFilterChain.filter(serverWebExchange);
    }

    private Authentication createAuthentication(String blogUserJson) {
        BlogUser blogUser = JSONUtils.parseObject(blogUserJson, BlogUser.class);
        if (null == blogUser) {
            return null;
        }
        String username = blogUser.getUsername();
        String roles = blogUser.getRoles();
        String[] rolesArr = roles.split(Constants.COMMA);
        List<GrantedAuthority> grantedAuthorityList = Arrays.stream(rolesArr).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        User principal = new User(username, Constants.EMPTY, grantedAuthorityList);
        return new UsernamePasswordAuthenticationToken(principal, Constants.EMPTY, principal.getAuthorities());
    }
}
