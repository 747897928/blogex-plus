package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.dto.UpdatePasswordDTO;
import cn.edu.gxust.blogex.api.dto.UsersDTO;
import cn.edu.gxust.blogex.dao.po.UsersPO;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (Users)表服务接口
 *
 * @author zhaoyijie
 * @since 2022-06-11 19:12:19
 */
public interface UsersService extends IService<UsersPO> {

    /**
     * 通过用户名查询
     */
    UsersPO queryByUsername(String username);

    /**
     * 修改密码
     */
    int updatePassword(UpdatePasswordDTO updatePasswordDTO,String token);
}

