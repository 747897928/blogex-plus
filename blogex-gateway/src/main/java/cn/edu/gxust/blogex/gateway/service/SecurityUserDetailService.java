package cn.edu.gxust.blogex.gateway.service;

import cn.edu.gxust.blogex.gateway.dto.AuthDTO;
import cn.edu.gxust.blogex.gateway.entity.BlogAdmin;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Service
public class SecurityUserDetailService implements ReactiveUserDetailsService {

    @Resource
    private BlogAdmin blogAdmin;

    private final PasswordEncoder passwordEncoder;

    public SecurityUserDetailService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 验证身份，接收authenticationManager.authenticate传过来的username，拿着username去查库，返回用户的账号和密码角色等信息
     *
     * @param username 用户名
     * @see cn.edu.gxust.blogex.gateway.service.AuthService#login(AuthDTO)
     */
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        // 后期从数据库取
        if (!blogAdmin.getUserName().equals(username)) {
            return Mono.empty();
        }
        UserDetails user = User.withUsername(username).password(passwordEncoder.encode(blogAdmin.getPassWord()))
                .roles(blogAdmin.getRole()).authorities(blogAdmin.getRole()).build();
        return Mono.just(user);
    }

}
