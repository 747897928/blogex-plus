package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.dao.mappers.RolesMapper;
import cn.edu.gxust.blogex.dao.po.RolesPO;
import cn.edu.gxust.blogex.api.service.RolesService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * (Roles)表服务实现类
 *
 * @author zhaoyijie
 * @since 2022-06-11 19:12:18
 */
@Service(value = "rolesServiceImpl")
public class RolesServiceImpl extends ServiceImpl<RolesMapper, RolesPO> implements RolesService {

    private static final Logger logger = LoggerFactory.getLogger(RolesServiceImpl.class);

    @Override
    public RolesPO queryByUsername(String username) {
        return getOne(Wrappers.<RolesPO>lambdaQuery().eq(RolesPO::getUsername, username));
    }

}

