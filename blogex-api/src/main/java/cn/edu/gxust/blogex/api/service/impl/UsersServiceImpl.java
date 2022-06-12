package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.dto.UpdatePasswordDTO;
import cn.edu.gxust.blogex.api.entity.BlogUser;
import cn.edu.gxust.blogex.api.service.RsaService;
import cn.edu.gxust.blogex.api.service.VerifyCodeService;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.enums.EnabledEnum;
import cn.edu.gxust.blogex.common.exception.BlogException;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import cn.edu.gxust.blogex.dao.mappers.UsersMapper;
import cn.edu.gxust.blogex.dao.po.UsersPO;
import cn.edu.gxust.blogex.api.service.UsersService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * (Users)表服务实现类
 *
 * @author zhaoyijie
 * @since 2022-06-11 19:12:19
 */
@Service(value = "usersServiceImpl")
public class UsersServiceImpl extends ServiceImpl<UsersMapper, UsersPO> implements UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Resource
    private RsaService rsaService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private VerifyCodeService verifyCodeService;

    @Override
    public UsersPO queryByUsername(String username) {
        return getOne(Wrappers.<UsersPO>lambdaQuery().eq(UsersPO::getUsername, username));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePassword(UpdatePasswordDTO updatePasswordDTO, String token) {
        String codeUuid = updatePasswordDTO.getCodeUuid();
        String verifyCode = updatePasswordDTO.getVerifyCode();
        /*校验验证码的有效性*/
        verifyCodeService.validateLoginCode(codeUuid, verifyCode);
        String blogUserJson = stringRedisTemplate.opsForValue().get(Constants.USER_CACHE_PREFIX_KEY + token);
        if (Objects.isNull(blogUserJson)) {
            throw new BlogException("不存在的用户");
        }
        BlogUser blogUser = JSONUtils.parseObject(blogUserJson, BlogUser.class);
        String username = blogUser.getUsername();
        String oldPassword = updatePasswordDTO.getOldPassword();
        String newPassword = updatePasswordDTO.getNewPassword();
        oldPassword = rsaService.decrypt(oldPassword);
        newPassword = rsaService.decrypt(newPassword);
        if (oldPassword.equals(newPassword)) {
            throw new BlogException("新旧密码不能一致");
        }
        UsersPO usersPO = queryByUsername(username);
        if (Objects.isNull(usersPO)) {
            throw new UsernameNotFoundException("未找到名为：" + username + "的用户");
        }
        if (!passwordEncoder.matches(oldPassword, usersPO.getPassword())) {
            throw new BlogException("旧密码不正确");
        }
        if (EnabledEnum.OFF.getCode() == usersPO.getEnabled()) {
            throw new BlogException(username + "用户正被禁用");
        }
        String encodePassword = passwordEncoder.encode(newPassword);
        usersPO.setPassword(encodePassword);
        int count = baseMapper.updateById(usersPO);
        if (count > 0) {
            stringRedisTemplate.delete(Constants.USER_CACHE_PREFIX_KEY + token);
        }
        return count;
    }

}

