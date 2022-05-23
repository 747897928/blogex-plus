package cn.edu.gxust.blogex.api.filter;

import cn.edu.gxust.blogex.api.entity.BlogUser;
import cn.edu.gxust.blogex.api.utils.NetworkUtils;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.enums.ErrorCode;
import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>description: </p>
 * <p>create: 2021/2/8 22:10 </p>
 *
 * @author :zhaoyijie
 */
public class CustomTokenVerifyFilter extends OncePerRequestFilter {

    private final StringRedisTemplate stringRedisTemplate;

    public CustomTokenVerifyFilter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);
        String token = requestWrapper.getHeader(Constants.AUTHORIZATION);
        requestWrapper.addHeader(Constants.AUTHORIZATION, token);
        if (null != token) {
            String cacheKey = Constants.USER_CACHE_PREFIX_KEY + token;
            String blogUserJson = stringRedisTemplate.opsForValue().get(cacheKey);
            if (null != blogUserJson) {
                Authentication authentication = createAuthentication(blogUserJson);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(requestWrapper, response);
                return;
            } else {
                Result<String> result = Result.error(ErrorCode.MOVED_TEMPORARILY);
                response.setStatus(HttpStatus.OK.value());
                response.setContentType("application/json;charset=UTF-8");
                String body = JSONUtils.toJsonString(result);
                response.getWriter().write(body);
                return;
            }
        }
        filterChain.doFilter(requestWrapper, response);
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
