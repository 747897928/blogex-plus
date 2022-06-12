package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.dto.RolesDTO;
import cn.edu.gxust.blogex.dao.po.RolesPO;
import cn.edu.gxust.blogex.api.vo.RolesVO;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhaoyijie
 * @since 2022-06-11 19:12:18
 */
public class RolesConvertor {

    private RolesConvertor() {
        throw new IllegalStateException("Construct RolesConvertor");
    }

    public static RolesPO convert(RolesDTO rolesDTO) {
        if (rolesDTO == null) {
            return null;
        }
        RolesPO rolesPO = new RolesPO();
        rolesPO.setId(rolesDTO.getId());
        rolesPO.setUsername(rolesDTO.getUsername());
        rolesPO.setRole(rolesDTO.getRole());
        return rolesPO;
    }

    public static RolesVO convert(RolesPO rolesPO) {
        if (rolesPO == null) {
            return null;
        }
        RolesVO rolesVO = new RolesVO();
        rolesVO.setId(rolesPO.getId());
        rolesVO.setUsername(rolesPO.getUsername());
        rolesVO.setRole(rolesPO.getRole());
        return rolesVO;
    }

    public static List<RolesVO> convert(List<RolesPO> rolesPOList) {
        if (rolesPOList == null) {
            return null;
        }
        List<RolesVO> rolesVOList = new ArrayList<>(rolesPOList.size());
        for (RolesPO rolesPO : rolesPOList) {
            if (rolesPO == null) {
                continue;
            }
            RolesVO rolesVO = new RolesVO();
            rolesVO.setId(rolesPO.getId());
            rolesVO.setUsername(rolesPO.getUsername());
            rolesVO.setRole(rolesPO.getRole());
            rolesVOList.add(rolesVO);
        }
        return rolesVOList;
    }

}

