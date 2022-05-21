package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.dto.ClassifyDTO;
import cn.edu.gxust.blogex.api.query.ClassifyPageQuery;
import cn.edu.gxust.blogex.api.dto.UpdateClassifyDTO;
import cn.edu.gxust.blogex.api.vo.ClassifyVO;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.dao.po.ClassifyPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/12 12:11
 */
public interface ClassifyService extends IService<ClassifyPO> {

    /**
     * 查询全部分类
     *
     * @return 分类及引用该分类文章的数量
     */
    List<ClassifyVO> selectAll();

    /**
     * 新增
     *
     * @param classifyDTO 分类
     * @return 受影响的行数
     */
    int insert(ClassifyDTO classifyDTO);

    /**
     * 更新
     *
     * @return 受影响的行数
     */
    int update(UpdateClassifyDTO updateClassifyDTO);

    /**
     * 根据id查找
     *
     * @param id 分类id
     * @return 分类
     */
    ClassifyPO selectById(Integer id);

    /**
     * 检查这个分类是否存在
     *
     * @param id 分类id
     */
    ClassifyPO checkExist(Integer id);

    /**
     * 条件查询
     */
    Pagination<ClassifyVO> listPage(ClassifyPageQuery classifyQuery);

    /**
     * 批量删除
     *
     * @param idList 分类id集合
     * @return 受影响的行数
     */
    int batchDelete(List<Integer> idList);

    /**
     * id查询
     */
    ClassifyVO detail(Integer id);

}
