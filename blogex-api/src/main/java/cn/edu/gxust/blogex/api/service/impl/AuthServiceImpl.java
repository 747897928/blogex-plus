package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.dto.AuthDTO;
import cn.edu.gxust.blogex.api.entity.BlogUser;
import cn.edu.gxust.blogex.api.service.AuthService;
import cn.edu.gxust.blogex.api.service.VerifyCodeService;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.enums.ErrorCode;
import cn.edu.gxust.blogex.common.exception.BlogException;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import cn.edu.gxust.blogex.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhaoyijie
 * @since 2022/5/23 15:53
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final static Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private VerifyCodeService verifyCodeService;

    @Value("${blogex.token.validate.times:36000}")
    private Integer tokenValidateTime;

    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 验证身份，authenticationManager会调用UserDetailsService里的loadUserByUsername
     * 在loadUserByUsername方法里查询数据库拿到账号密码，然后与这里的UsernamePasswordAuthenticationToken里的账号密码配对，
     * 如果配对成功则认证通过，反之鉴权失败
     *
     * @see cn.edu.gxust.blogex.api.service.impl.UserDetailsServiceImpl#loadUserByUsername (String)
     */
    public BlogUser login(AuthDTO authDTO) {
        String verifyCode = authDTO.getVerifyCode();
        String codeUuid = authDTO.getCodeUuid();
        //校验滑动验证码的有效性
        verifyCodeService.validateLoginCode(codeUuid, verifyCode);
        String username = authDTO.getUsername();
        String passWord = authDTO.getPassword();
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, passWord);
        Authentication authentication = authenticationManager.authenticate(passwordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
        List<String> rolesList = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String roles = StringUtils.join(rolesList, Constants.COMMA);
        User principal = (User) authentication.getPrincipal();
        BlogUser blogUser = BlogUser.newBuilder().username(principal.getUsername()).roles(roles).build();
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
        return blogUser;

    }

    public String logout(String token) {
        if (token != null) {
            String cacheKey = Constants.USER_CACHE_PREFIX_KEY + token;
            if (Boolean.TRUE.equals(stringRedisTemplate.delete(cacheKey))) {
                return "logout success!";
            }
        }
        throw new BlogException(ErrorCode.SERVER_ERROR.getCode(), "logout fail,destroying the token failed!");
    }
}
