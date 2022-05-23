package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.dto.AuthDTO;
import cn.edu.gxust.blogex.api.entity.BlogUser;

/**
 * @author zhaoyijie
 * @since 2022/3/22 20:07
 */
public interface AuthService {

    BlogUser login(AuthDTO authDTO);

    String logout(String token);

}
