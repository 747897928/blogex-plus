package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.dto.ArticleDTO;
import cn.edu.gxust.blogex.api.vo.ArticleVO;
import cn.edu.gxust.blogex.api.vo.TagVO;
import cn.edu.gxust.blogex.dao.po.ArticlePO;
import cn.edu.gxust.blogex.dao.po.TagPO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoyijie
 * @since 2022/3/11 22:58
 */
public class ArticleConvertor {

    private ArticleConvertor() {
        throw new IllegalStateException("Construct ArticleConvertor");
    }

    public static ArticlePO convert(ArticleDTO articleDTO) {
        if (articleDTO == null) {
            return null;
        }
        ArticlePO articlePO = new ArticlePO();
        articlePO.setId(articleDTO.getId());
        articlePO.setTitle(articleDTO.getTitle());
        articlePO.setSnippet(articleDTO.getSnippet());
        articlePO.setClassifyId(articleDTO.getClassifyId());
        articlePO.setContent(articleDTO.getContent());
        articlePO.setReviewImgUrl(articleDTO.getReviewImgUrl());
        articlePO.setViewCount(articleDTO.getViewCount());
        articlePO.setSupportCount(articleDTO.getSupportCount());
        articlePO.setContentType(articleDTO.getContentType());
        articlePO.setPriority(articleDTO.getPriority());
        articlePO.setArticleStatus(articleDTO.getArticleStatus());
        articlePO.setCommentStatus(articleDTO.getCommentStatus());
        articlePO.setPostType(articleDTO.getPostType());
        return articlePO;
    }

    public static ArticleVO convert(ArticlePO articlePO) {
        if (articlePO == null) {
            return null;
        }
        ArticleVO articleVO = new ArticleVO();
        articleVO.setId(articlePO.getId());
        articleVO.setTitle(articlePO.getTitle());
        articleVO.setSnippet(articlePO.getSnippet());
        articleVO.setClassifyId(articlePO.getClassifyId());
        articleVO.setClassifyName(articlePO.getClassifyName());
        articleVO.setSumComment(articlePO.getSumComment());
        articleVO.setContent(articlePO.getContent());
        articleVO.setReviewImgUrl(articlePO.getReviewImgUrl());
        articleVO.setViewCount(articlePO.getViewCount());
        articleVO.setSupportCount(articlePO.getSupportCount());
        articleVO.setContentType(articlePO.getContentType());
        articleVO.setPriority(articlePO.getPriority());
        articleVO.setArticleStatus(articlePO.getArticleStatus());
        articleVO.setCommentStatus(articlePO.getCommentStatus());
        articleVO.setPostType(articlePO.getPostType());
        articleVO.setCreateTime(articlePO.getCreateTime());
        articleVO.setUpdateTime(articlePO.getUpdateTime());
        return articleVO;
    }

    public static List<ArticleVO> convert(List<ArticlePO> articlePOList) {
        if (articlePOList == null) {
            return null;
        }
        List<ArticleVO> articleVOList = new ArrayList<>(articlePOList.size());
        for (ArticlePO articlePO : articlePOList) {
            if (articlePO == null) {
                continue;
            }
            ArticleVO articleVO = new ArticleVO();
            articleVO.setId(articlePO.getId());
            articleVO.setTitle(articlePO.getTitle());
            articleVO.setSnippet(articlePO.getSnippet());
            articleVO.setClassifyId(articlePO.getClassifyId());
            articleVO.setClassifyName(articlePO.getClassifyName());
            articleVO.setSumComment(articlePO.getSumComment());
            articleVO.setContent(articlePO.getContent());
            articleVO.setReviewImgUrl(articlePO.getReviewImgUrl());
            articleVO.setViewCount(articlePO.getViewCount());
            articleVO.setSupportCount(articlePO.getSupportCount());
            articleVO.setContentType(articlePO.getContentType());
            articleVO.setPriority(articlePO.getPriority());
            articleVO.setArticleStatus(articlePO.getArticleStatus());
            articleVO.setCommentStatus(articlePO.getCommentStatus());
            articleVO.setPostType(articlePO.getPostType());
            articleVO.setCreateTime(articlePO.getCreateTime());
            articleVO.setUpdateTime(articlePO.getUpdateTime());
            articleVO.setTagList(TagConvertor.convert(articlePO.getTagList()));
            articleVOList.add(articleVO);
        }
        return articleVOList;
    }

    public static void assemble(List<ArticleVO> resultList, Map<Integer, List<TagPO>> tagMap) {
        for (ArticleVO articleVO : resultList) {
            Integer articleId = articleVO.getId();
            List<TagPO> tagPOList = tagMap.get(articleId);
            List<TagVO> tagVOList = TagConvertor.convert(tagPOList);
            articleVO.setTagList(tagVOList);
        }
    }
}
