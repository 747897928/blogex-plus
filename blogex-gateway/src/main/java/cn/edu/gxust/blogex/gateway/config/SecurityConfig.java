package cn.edu.gxust.blogex.gateway.config;

import cn.edu.gxust.blogex.gateway.entity.BlogAdmin;
import cn.edu.gxust.blogex.gateway.filter.CustomTokenVerifyFilter;
import cn.edu.gxust.blogex.gateway.handler.CustomAccessDeniedHandler;
import cn.edu.gxust.blogex.gateway.handler.CustomHttpBasicServerAuthenticationEntryPoint;
import cn.edu.gxust.blogex.gateway.service.SecurityUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

/**
 * @author zhaoyijie
 * @since 2022/3/21 12:10
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {


    @Resource
    private BlogAdmin blogAdmin;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 允许多请求地址多加斜杠  比如 /msg/list   //msg/list
     */
    /*@Bean
    public HttpFirewall httpFirewall() {
        return new DefaultHttpFirewall();
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 实现该处的ReactiveUserDetailsService
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(SecurityUserDetailService userDetailsService, PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         CustomHttpBasicServerAuthenticationEntryPoint customHttpBasicServerAuthenticationEntryPoint,
                                                         CustomAccessDeniedHandler accessDeniedHandler,
                                                         ReactiveAuthenticationManager reactiveAuthenticationManager) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authenticationManager(reactiveAuthenticationManager)
                .exceptionHandling()
                .authenticationEntryPoint(customHttpBasicServerAuthenticationEntryPoint)// 自定义authenticationEntryPoint
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange()
                .pathMatchers(blogAdmin.getPermissionList()).permitAll()// 白名单
                .pathMatchers(HttpMethod.OPTIONS).permitAll()// option请求默认放行
                .anyExchange()
                .authenticated()
                .and()
                .headers().frameOptions().disable()
                .and()
                /*开启跨域*/
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .addFilterAt(new CustomTokenVerifyFilter(stringRedisTemplate), SecurityWebFiltersOrder.HTTP_BASIC);
        return http.build();
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
