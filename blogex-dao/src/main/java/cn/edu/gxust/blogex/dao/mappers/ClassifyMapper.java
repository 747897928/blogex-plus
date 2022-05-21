package cn.edu.gxust.blogex.dao.mappers;

import cn.edu.gxust.blogex.dao.po.ClassifyPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/12 08:34
 */
@Mapper
public interface ClassifyMapper extends BaseMapper<ClassifyPO> {

    List<ClassifyPO> selectAllAndRefCount();

}
