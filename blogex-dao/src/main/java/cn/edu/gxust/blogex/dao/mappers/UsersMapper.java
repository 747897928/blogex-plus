package cn.edu.gxust.blogex.dao.mappers;

import cn.edu.gxust.blogex.dao.po.UsersPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Users)表数据库访问层
 *
 * @author zhaoyijie
 * @since 2022-06-11 19:12:19
 */
@Mapper
public interface UsersMapper extends BaseMapper<UsersPO> {

}

