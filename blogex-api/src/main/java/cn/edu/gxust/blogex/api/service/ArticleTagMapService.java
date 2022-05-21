package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.dao.po.ArticleTagMapPO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 文章与标签的mapping表对应的服务类
 *
 * @author zhaoyijie
 * @since 2022/3/12 10:10
 */
public interface ArticleTagMapService extends IService<ArticleTagMapPO> {

    /**
     * 给文章批量新增标签
     *
     * @param tagIdCollection 标签id集合
     * @param articleId       文章id
     * @return 批量新增是否成功
     */
    boolean batchInsert(Collection<Integer> tagIdCollection, Integer articleId);

    /**
     * 查询一个文章对应的标签id
     *
     * @param articleId 文章id
     * @return 标签id列表
     */
    List<Integer> getByArticleId(Integer articleId);

    /**
     * 删除文章标签映射记录
     *
     * @param articleId 文章id
     * @return 受影响的行数
     */
    int deleteByArticleId(Integer articleId);

    /**
     * 更新文章的标签
     *
     * @param tagIdCollection 标签id集合
     * @param articleId       文章id
     * @return 受影响的行数
     */
    int update(Collection<Integer> tagIdCollection, Integer articleId);

    /**
     * 删除一条文章标签映射记录
     *
     * @param tagId     标签id
     * @param articleId 文章id
     * @return 受影响的行数
     */
    int delete(Integer tagId, Integer articleId);

    /**
     * 根据tagId查询articleId
     *
     * @param tagId 标签id
     * @return a List of articleId
     */
    List<Integer> getArticleIdByTagId(Integer tagId);


    /**
     * 根据tagId分页查询articleId
     *
     * @param tagId 标签id
     */
    Pagination<Integer> listPageArticleIdByTagId(Integer tagId, long pageNo, long pageSize);

}
