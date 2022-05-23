package cn.edu.gxust.blogex.api.config;

import cn.edu.gxust.blogex.api.entity.BlogAdmin;
import cn.edu.gxust.blogex.api.filter.CustomTokenVerifyFilter;
import cn.edu.gxust.blogex.api.handler.BlogAccessDeniedHandler;
import cn.edu.gxust.blogex.common.enums.ErrorCode;
import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p>description:  web安全配置类</p>
 * <p>create: 2020/2/2 21:01</p>
 *
 * @author zhaoyijie
 * @version v1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private BlogAdmin blogAdmin;

    @Resource
    private BlogAccessDeniedHandler accessDeniedHandler;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 允许多请求地址多加斜杠  比如 /msg/list   //msg/list
     */
    @Bean
    public HttpFirewall httpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*authorize权限 authenticatedr认证 anyRequest()任何请求 authenticated认证后才能访问
        authorizeRequests所有security全注解配置实现的开端，表示开始说明需要的权限。
        需要的权限分两部分，第一部分是拦截的路径， 第二部分访问该路径需要的权限。
        .and().csrf().disable();使csrf拦截失效,我们这里允许基本的查询操作，但是只有管理员有全部权限，
        游客只能查看无增删改权限*/
        http.cors().configurationSource(corsConfigurationSource()); /*开启跨域*/
        http.headers().frameOptions().disable();
        http.csrf().disable();
        /*在验证账号和密码之前先验证登录验证码是否正确*/
        http.httpBasic().disable().logout().disable().rememberMe().disable();
        /*限制登录数量为1，maxSessionsPreventsLogin表示是否保留已经登录的用户；为true，
        新用户无法登录；为 false，旧用户被踢出*/
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new CustomTokenVerifyFilter(stringRedisTemplate), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(new BasicAuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
                response.setStatus(HttpStatus.OK.value());
                response.setContentType("application/json;charset=UTF-8");
                //设置body
                String body = JSONUtils.toJsonString(Result.error(ErrorCode.UNAUTHORIZED.getCode(), "your operation is not authorized"));
                response.getWriter().write(body);
            }
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行资源
        web.ignoring().antMatchers(blogAdmin.getPermissionList());
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //配置跨域访问资源
    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");// header，允许哪些header
        corsConfiguration.addAllowedMethod("*"); // 允许的请求方法，PSOT、GET等
        source.registerCorsConfiguration("/**", corsConfiguration); // 配置允许跨域访问的url
        return source;
    }
}
