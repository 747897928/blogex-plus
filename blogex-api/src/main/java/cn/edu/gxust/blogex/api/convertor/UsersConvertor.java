package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.dto.UsersDTO;
import cn.edu.gxust.blogex.dao.po.UsersPO;
import cn.edu.gxust.blogex.api.vo.UsersVO;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhaoyijie
 * @since 2022-06-11 19:12:19
 */
public class UsersConvertor {

    private UsersConvertor() {
        throw new IllegalStateException("Construct UsersConvertor");
    }

    public static UsersPO convert(UsersDTO usersDTO) {
        if (usersDTO == null) {
            return null;
        }
        UsersPO usersPO = new UsersPO();
        usersPO.setId(usersDTO.getId());
        usersPO.setUsername(usersDTO.getUsername());
        usersPO.setPassword(usersDTO.getPassword());
        usersPO.setEnabled(usersDTO.getEnabled());
        return usersPO;
    }

    public static UsersVO convert(UsersPO usersPO) {
        if (usersPO == null) {
            return null;
        }
        UsersVO usersVO = new UsersVO();
        usersVO.setId(usersPO.getId());
        usersVO.setUsername(usersPO.getUsername());
        usersVO.setPassword(usersPO.getPassword());
        usersVO.setEnabled(usersPO.getEnabled());
        return usersVO;
    }

    public static List<UsersVO> convert(List<UsersPO> usersPOList) {
        if (usersPOList == null) {
            return null;
        }
        List<UsersVO> usersVOList = new ArrayList<>(usersPOList.size());
        for (UsersPO usersPO : usersPOList) {
            if (usersPO == null) {
                continue;
            }
            UsersVO usersVO = new UsersVO();
            usersVO.setId(usersPO.getId());
            usersVO.setUsername(usersPO.getUsername());
            usersVO.setPassword(usersPO.getPassword());
            usersVO.setEnabled(usersPO.getEnabled());
            usersVOList.add(usersVO);
        }
        return usersVOList;
    }

}

