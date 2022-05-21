package cn.edu.gxust.blogex.dao.mappers;

import cn.edu.gxust.blogex.dao.po.OverviewPO;
import cn.edu.gxust.blogex.dao.po.TagPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.edu.gxust.blogex.dao.po.ArticlePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2021/7/23 12:05
 */
@Mapper
public interface ArticleMapper extends BaseMapper<ArticlePO> {

    /**
     * 根据文章id查找文章标题
     */
    String findTitle(@Param(value = "articleId") Integer articleId);

    /**
     * 当分类表更新名字的时候，需要查询分类表，把分类表的名字取出来更新文章表里分类名这个冗余的字段
     */
    int updateClassifyName(@Param(value = "classifyId") Integer classifyId);

    /**
     * 查询评论表，把评论的数量取出来更新文章表里评论数量这个冗余的字段
     */
    int updateSumComment(@Param(value = "articleId") Integer articleId);

    /**
     * 查询评论表，把评论的数量取出来更新文章表里评论数量这个冗余的字段
     */
    int updateSumCommentByList(@Param(value = "articleIdList") List<Integer> articleIdList);

    /**
     * 获取博客总览信息，包括：总文章数，总阅览量，总评论，总用户数
     */
    OverviewPO getBlogOverview();

    /**
     * 随机查询10条文章
     */
    List<ArticlePO> getRandomArticle();

    /**
     * 条件查询
     */
    List<ArticlePO> listQuery(
            @Param(value = "idList") List<Integer> idList,
            @Param(value = "searchKey") String searchKey,
            @Param(value = "classifyId") Integer classifyId,
            @Param(value = "contentType") Integer contentType,
            @Param(value = "articleStatus") Integer articleStatus,
            @Param(value = "commentStatus") Integer commentStatus,
            @Param(value = "postType") Integer postType,
            @Param(value = "startTime") Date startTime,
            @Param(value = "endTime") Date endTime);

    /**
     * 根据文章id查询对应的tag
     */
    List<TagPO> queryByArticleId(@Param(value = "articleId") Integer articleId);

}
