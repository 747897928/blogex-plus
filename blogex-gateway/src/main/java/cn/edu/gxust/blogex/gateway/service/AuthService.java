package cn.edu.gxust.blogex.gateway.service;

import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import cn.edu.gxust.blogex.common.utils.StringUtils;
import cn.edu.gxust.blogex.gateway.dto.AuthDTO;
import cn.edu.gxust.blogex.gateway.entity.BlogUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhaoyijie
 * @since 2022/3/22 20:07
 */
@Service
public class AuthService {

    private final static Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Resource
    private ReactiveAuthenticationManager authenticationManager;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private VerifyCodeService verifyCodeService;

    @Value("${blogex.token.validate.times:36000}")
    private Integer tokenValidateTime;

    /**
     * 验证身份，authenticate会调用ReactiveAuthenticationManager的ReactiveUserDetailsService里的findByUsername
     * 在findByUsername方法里查询数据库拿到账号密码，然后与这里的UsernamePasswordAuthenticationToken里的账号密码配对，
     * 如果配对成功则认证通过，反之鉴权失败
     *
     * @see cn.edu.gxust.blogex.gateway.service.SecurityUserDetailService#findByUsername(String)
     */
    public Mono<Result<BlogUser>> login(AuthDTO authDTO) {
        String verifyCode = authDTO.getVerifyCode();
        String codeUuid = authDTO.getCodeUuid();
        //校验滑动验证码的有效性
        verifyCodeService.validate(codeUuid, verifyCode);
        String username = authDTO.getUsername();
        String passWord = authDTO.getPassword();
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, passWord);
        Mono<Authentication> authenticationMono = authenticationManager.authenticate(passwordAuthenticationToken);
        return authenticationMono.map(authentication -> {
            //String credential = (String) authentication.getCredentials();//本质上是加密后的密码
            List<String> rolesList = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            String roles = StringUtils.join(rolesList, Constants.COMMA);
            User principal = (User) authentication.getPrincipal();
            BlogUser blogUser = BlogUser.newBuilder().username(principal.getUsername()).roles(roles).build();
            return Result.success(blogUser);
        }).doOnSuccess(result -> {
                    BlogUser blogUser = result.getData();
                    String token = UUID.randomUUID().toString();
                    blogUser.setToken(token);
                    String blogUserJson = JSONUtils.toJsonString(blogUser);
                    String cacheKey = Constants.USER_CACHE_PREFIX_KEY + token;
                    //清空全部的登录信息
                    Set<String> keys = stringRedisTemplate.keys(Constants.USER_CACHE_PREFIX_KEY + Constants.STAR);
                    if (null != keys) {
                        stringRedisTemplate.delete(keys);
                    }
                    stringRedisTemplate.opsForValue().set(cacheKey, blogUserJson, tokenValidateTime, TimeUnit.SECONDS);
                    //response.addCookie(ResponseCookie.from(Constants.JSESSIONID, token).maxAge(tokenValidateTime).build());
                }
        );
    }

    public Mono<Result<String>> logout(String token) {
        Result<String> result = Result.error("logout fail,destroying the token failed!");
        if (token != null) {
            String cacheKey = Constants.USER_CACHE_PREFIX_KEY + token;
            if (Boolean.TRUE.equals(stringRedisTemplate.delete(cacheKey))) {
                result = Result.success("logout success!");
            }
        }
        return Mono.just(result);
    }

    /**
     * Clears all the cookies from the response
     */
    public void deleteCookies(ServerHttpRequest request, ServerHttpResponse response) {
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();

        for (Map.Entry<String, List<HttpCookie>> cookie : cookies.entrySet()) {
            for (HttpCookie cookieToBeDeleted : cookie.getValue()) {
                logger.debug("Deleting cookie: {} having value: {}", cookieToBeDeleted.getName(), cookieToBeDeleted
                        .getValue());
                response.addCookie(ResponseCookie.from(cookieToBeDeleted.getName(), cookieToBeDeleted.getValue())
                        .maxAge(0).build());
            }
        }
        logger.debug("Response cookies" + response.getCookies().toString());
    }
}
