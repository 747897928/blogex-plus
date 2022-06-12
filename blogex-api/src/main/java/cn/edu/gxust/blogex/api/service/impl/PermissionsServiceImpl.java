package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.dao.mappers.PermissionsMapper;
import cn.edu.gxust.blogex.dao.po.PermissionsPO;
import cn.edu.gxust.blogex.api.service.PermissionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * (Permissions)表服务实现类
 *
 * @author zhaoyijie
 * @since 2022-06-11 19:12:18
 */
@Service(value = "permissionsServiceImpl")
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper, PermissionsPO> implements PermissionsService {

    private static final Logger logger = LoggerFactory.getLogger(PermissionsServiceImpl.class);

}

