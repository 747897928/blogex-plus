package cn.edu.gxust.blogex.dao.mappers;

import cn.edu.gxust.blogex.dao.po.TagPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/12 08:35
 */
@Mapper
public interface TagMapper extends BaseMapper<TagPO> {

    List<TagPO> getByArticleIdList(@Param(value = "articleIdList") List<Integer> articleIdList);

    List<TagPO> selectAllAndRefCount();

}
