package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.dto.TagDTO;
import cn.edu.gxust.blogex.api.query.TagPageQuery;
import cn.edu.gxust.blogex.api.dto.UpdateTagDTO;
import cn.edu.gxust.blogex.api.vo.TagVO;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.dao.po.TagPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author zhaoyijie
 * @since 2022/3/12 13:32
 */
public interface TagService extends IService<TagPO> {

    /**
     * 根据文章id查找对应的tag
     *
     * @param articleId 文章id
     * @return tag列表
     */
    List<TagVO> getByArticleId(Integer articleId);

    /**
     * 根据文章id查找对应的tag
     *
     * @param articleIdList 文章id集合
     * @return 文章id->tag列表
     */
    Map<Integer, List<TagPO>> getByArticleIdList(List<Integer> articleIdList);

    /**
     * 新增
     */
    int insert(TagDTO tagDTO);

    /**
     * 批量删除
     *
     * @param idList 标签id
     * @return 受影响的行数
     */
    int batchDelete(List<Integer> idList);

    /**
     * 更新
     */
    int update(UpdateTagDTO updateTagDTO);

    /**
     * 校验是否存在
     *
     * @param id 标签id
     * @return 标签映射
     * @throws cn.edu.gxust.blogex.common.exception.TagNotFoundException if not exist
     */
    TagPO checkExist(Integer id);

    /**
     * 条件查询
     */
    Pagination<TagVO> listPage(TagPageQuery tagQuery);

    /**
     * 查询全部标签
     */
    List<TagVO> queryAll();

    /**
     * 根据id查询标签详情
     */
    TagVO detail(Integer id);

}
