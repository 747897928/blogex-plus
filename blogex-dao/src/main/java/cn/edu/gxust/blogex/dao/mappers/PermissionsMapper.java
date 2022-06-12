package cn.edu.gxust.blogex.dao.mappers;

import cn.edu.gxust.blogex.dao.po.PermissionsPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Permissions)表数据库访问层
 *
 * @author zhaoyijie
 * @since 2022-06-11 19:12:18
 */
@Mapper
public interface PermissionsMapper extends BaseMapper<PermissionsPO> {

}

