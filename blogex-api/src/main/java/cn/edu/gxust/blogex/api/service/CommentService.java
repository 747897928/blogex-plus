package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.dto.CommentDTO;
import cn.edu.gxust.blogex.api.dto.CommentVerifyCodeDTO;
import cn.edu.gxust.blogex.api.query.CommentPageQuery;
import cn.edu.gxust.blogex.api.query.CommentQueryV2;
import cn.edu.gxust.blogex.api.query.CommentQueryV3;
import cn.edu.gxust.blogex.api.vo.BloggerInfoVO;
import cn.edu.gxust.blogex.api.vo.CommentVO;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.dao.po.CommentPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/18 23:16
 */
public interface CommentService extends IService<CommentPO> {

    /**
     * 新增一条评论
     */
    int insert(CommentVerifyCodeDTO commentVerifyCodeDTO, String userAgentStr, String userIp);


    /**
     * 新增一条评论
     */
    int insert(CommentDTO commentDTO, String userAgentStr, String userIp, BloggerInfoVO bloggerInfo);

    /**
     * 添加评论，该接口是给博主回复用户评论用的。
     */
    int bloggerReplyComment(CommentDTO commentDTO, String userAgentStr, String userIp);

    /**
     * 根据文章id删除对应的评论
     *
     * @param articleId 文章id
     * @return 受影响的行数
     */
    int deleteByArticleId(Integer articleId);

    /**
     * 根据文章id分页查询对应的父评论，注意！是父评论，不包含子评论，这里只会查询文章评论类型的评论
     */
    Pagination<CommentPO> listPageParent(Integer articleId, Long pageNo, Long pageSize);

    /**
     * 根据页面类型分页查询对应的父评论，注意！是父评论，不包含子评论，这里只会查询文章评论类型的评论
     */
    Pagination<CommentPO> listPageParentByPageType(Integer pageType, Long pageNo, Long pageSize);

    /**
     * 条件查询
     */
    Pagination<CommentVO> listPage(CommentPageQuery query);

    /**
     * 根据文章的id，分页查询该文章的评论
     */
    Pagination<CommentVO> listPageByArticleId(CommentQueryV2 queryV2);


    /**
     * 根据页面类型，分页查询该文章的评论
     */
    Pagination<CommentVO> listPageByPageType(CommentQueryV3 queryV3);

    /**
     * 批量删除
     *
     * @param idList 评论id
     * @return 受影响的行数
     */
    int batchDelete(List<Integer> idList);

}
