package cn.edu.gxust.blogex.dao.mappers;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import cn.edu.gxust.blogex.dao.po.BlogLinkPO;

import java.util.Date;


/**
 * (BlogLink)表数据库访问层
 *
 * @author zhaoyijie
 * @since 2022-03-20 17:09:24
 */
@Mapper
public interface BlogLinkMapper extends BaseMapper<BlogLinkPO> {

}

