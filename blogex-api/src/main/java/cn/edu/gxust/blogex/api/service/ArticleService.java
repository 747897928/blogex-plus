package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.dto.ArticleDTO;
import cn.edu.gxust.blogex.api.query.ArticlePageQuery;
import cn.edu.gxust.blogex.api.vo.ArticleVO;
import cn.edu.gxust.blogex.api.vo.OverviewVO;
import cn.edu.gxust.blogex.common.enums.CommentStatus;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.dao.po.ArticlePO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/2/26 11:29
 */
public interface ArticleService extends IService<ArticlePO> {

    /**
     * 新增文章
     *
     * @param articleDTO 待插入的文章对象
     * @return 受影响的行数
     */
    int addArticle(ArticleDTO articleDTO);

    /**
     * 修改文章
     *
     * @param articleDTO articleDTO 待更新的文章对象
     * @return 受影响的行数
     */
    int updateArticle(ArticleDTO articleDTO);

    /**
     * 增加文章阅览量
     *
     * @param articleId 文章的id
     */
    int increaseArticleViewCount(Integer articleId);

    /**
     * 查询一篇文章
     *
     * @param id 文章id
     * @return 文章
     */
    ArticleVO getArticle(Integer id);

    /**
     * 删除一篇文章
     *
     * @param id 文章id
     * @return 受影响的行数
     */
    int deleteArticle(Integer id);

    /**
     * 获取这篇文章的评论状态
     *
     * @param articleId 文章id
     * @return 这篇文章的评论状态
     */
    CommentStatus getcommentStatus(Integer articleId);

    /**
     * 检查这篇文章是否存在，存在返回文章，不存在抛出异常
     *
     * @param articleId 文章id
     * @return 文章实体类
     * @throws cn.edu.gxust.blogex.common.exception.ArticleNotFoundException if not exist
     */
    ArticlePO checkExist(Integer articleId);

    /**
     * 分页查询
     */
    Pagination<ArticleVO> listPage(ArticlePageQuery articleQuery);

    /**
     * 批量删除文章
     *
     * @param idList 文章id
     * @return 受影响的行数
     */
    int batchDelete(List<Integer> idList);

    /**
     * 为指定文章加点赞数
     *
     * @param articleId 文章id
     * @return 受影响的行数
     */
    int increaseSupport(Integer articleId);

    /**
     * 当分类表更新名字的时候，需要查询分类表，把分类表的名字取出来更新文章表里分类名这个冗余的字段
     */
    int updateClassifyName(Integer classifyId);

    /**
     * 查询评论表，把评论的数量取出来更新文章表里评论数量这个冗余的字段
     */
    int updateSumComment(Integer articleId);

    /**
     * 查询评论表，把评论的数量取出来更新文章表里评论数量这个冗余的字段
     */
    int updateSumCommentByList(List<Integer> articleIdList);

    /**
     * in articleId
     */
    List<ArticlePO> findByInId(List<Integer> idList);

    /**
     * 获取阅览量前五的文章
     */
    List<ArticleVO> getTOPFiveArticle();

    /**
     * 获取评论最多的五篇文章
     */
    List<ArticleVO> getTopFiveSumCommentArticle();

    /**
     * 获取博客总览信息，包括：总文章数，总阅览量，总评论，总用户数
     */
    OverviewVO getBlogOverview();

    /**
     * 随机获取10条文章记录
     */
    List<ArticleVO> getRandomArticle();
}
