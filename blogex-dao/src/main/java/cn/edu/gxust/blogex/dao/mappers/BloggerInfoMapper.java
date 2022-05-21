package cn.edu.gxust.blogex.dao.mappers;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import cn.edu.gxust.blogex.dao.po.BloggerInfoPO;

import java.util.Date;


/**
 * (BloggerInfo)表数据库访问层
 *
 * @author zhaoyijie
 * @since 2022-03-20 17:10:45
 */
@Mapper
public interface BloggerInfoMapper extends BaseMapper<BloggerInfoPO> {

}

