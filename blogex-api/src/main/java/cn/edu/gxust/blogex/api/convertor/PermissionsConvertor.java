package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.dto.PermissionsDTO;
import cn.edu.gxust.blogex.dao.po.PermissionsPO;
import cn.edu.gxust.blogex.api.vo.PermissionsVO;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhaoyijie
 * @since 2022-06-11 19:12:18
 */
public class PermissionsConvertor {

    private PermissionsConvertor() {
        throw new IllegalStateException("Construct PermissionsConvertor");
    }

    public static PermissionsPO convert(PermissionsDTO permissionsDTO) {
        if (permissionsDTO == null) {
            return null;
        }
        PermissionsPO permissionsPO = new PermissionsPO();
        permissionsPO.setId(permissionsDTO.getId());
        permissionsPO.setRole(permissionsDTO.getRole());
        permissionsPO.setResource(permissionsDTO.getResource());
        permissionsPO.setAction(permissionsDTO.getAction());
        return permissionsPO;
    }

    public static PermissionsVO convert(PermissionsPO permissionsPO) {
        if (permissionsPO == null) {
            return null;
        }
        PermissionsVO permissionsVO = new PermissionsVO();
        permissionsVO.setId(permissionsPO.getId());
        permissionsVO.setRole(permissionsPO.getRole());
        permissionsVO.setResource(permissionsPO.getResource());
        permissionsVO.setAction(permissionsPO.getAction());
        return permissionsVO;
    }

    public static List<PermissionsVO> convert(List<PermissionsPO> permissionsPOList) {
        if (permissionsPOList == null) {
            return null;
        }
        List<PermissionsVO> permissionsVOList = new ArrayList<>(permissionsPOList.size());
        for (PermissionsPO permissionsPO : permissionsPOList) {
            if (permissionsPO == null) {
                continue;
            }
            PermissionsVO permissionsVO = new PermissionsVO();
            permissionsVO.setId(permissionsPO.getId());
            permissionsVO.setRole(permissionsPO.getRole());
            permissionsVO.setResource(permissionsPO.getResource());
            permissionsVO.setAction(permissionsPO.getAction());
            permissionsVOList.add(permissionsVO);
        }
        return permissionsVOList;
    }

}

