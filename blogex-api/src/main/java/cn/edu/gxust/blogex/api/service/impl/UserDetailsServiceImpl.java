package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.dto.AuthDTO;
import cn.edu.gxust.blogex.api.entity.BlogAdmin;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhaoyijie
 * @since 2022/5/23 15:39
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private BlogAdmin blogAdmin;

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 验证身份，接收authenticationManager.authenticate传过来的username，拿着username去查库，返回用户的账号和密码角色等信息
     *
     * @param username 用户名
     * @see cn.edu.gxust.blogex.api.service.AuthService#login(AuthDTO)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.withUsername(username).password(passwordEncoder.encode(blogAdmin.getPassWord()))
                .roles(blogAdmin.getRole()).authorities(blogAdmin.getRole()).build();
    }

}
