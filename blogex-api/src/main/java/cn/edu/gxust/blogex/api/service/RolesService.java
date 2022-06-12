package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.dao.po.RolesPO;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (Roles)表服务接口
 *
 * @author zhaoyijie
 * @since 2022-06-11 19:12:19
 */
public interface RolesService extends IService<RolesPO> {

    /**
     * 通过用户名查询
     */
    RolesPO queryByUsername(String username);

}

