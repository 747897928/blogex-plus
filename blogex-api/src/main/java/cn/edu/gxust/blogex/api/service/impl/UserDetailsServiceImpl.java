package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.dto.AuthDTO;
import cn.edu.gxust.blogex.api.service.RolesService;
import cn.edu.gxust.blogex.api.service.UsersService;
import cn.edu.gxust.blogex.common.enums.EnabledEnum;
import cn.edu.gxust.blogex.common.exception.BlogException;
import cn.edu.gxust.blogex.dao.po.RolesPO;
import cn.edu.gxust.blogex.dao.po.UsersPO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zhaoyijie
 * @since 2022/5/23 15:39
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UsersService usersService;

    @Resource
    private RolesService rolesService;

    /**
     * 验证身份，接收authenticationManager.authenticate传过来的username，拿着username去查库，返回用户的账号和密码角色等信息
     *
     * @param username 用户名
     * @see cn.edu.gxust.blogex.api.service.AuthService#login(AuthDTO)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersPO usersPO = usersService.queryByUsername(username);
        if (Objects.isNull(usersPO)) {
            throw new UsernameNotFoundException("未找到名为：" + username + "的用户");
        }
        if (EnabledEnum.OFF.getCode() == usersPO.getEnabled()) {
            throw new BlogException(username + "用户正被禁用");
        }
        RolesPO rolesPO = rolesService.queryByUsername(username);
        if (Objects.isNull(rolesPO)) {
            throw new BlogException(username + "用户还没有角色");
        }
        return User.withUsername(username).password(usersPO.getPassword())
                .roles(rolesPO.getRole()).authorities(rolesPO.getRole()).build();
    }

}
