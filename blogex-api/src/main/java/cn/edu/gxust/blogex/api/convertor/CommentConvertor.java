package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.dto.CommentDTO;
import cn.edu.gxust.blogex.api.vo.CommentVO;
import cn.edu.gxust.blogex.dao.po.CommentPO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/18 22:53
 */
public class CommentConvertor {

    private CommentConvertor() {
        throw new IllegalStateException("Construct CommentConverter");
    }

    public static CommentPO convert(CommentDTO commentDTO) {
        if (commentDTO == null) {
            return null;
        }
        CommentPO commentPO = new CommentPO();
        commentPO.setId(commentDTO.getId());
        commentPO.setArticleId(commentDTO.getArticleId());
        commentPO.setUserName(commentDTO.getUserName());
        commentPO.setUserEmail(commentDTO.getUserEmail());
        commentPO.setUserAvatarUrl(commentDTO.getUserAvatarUrl());
        commentPO.setCommentContent(commentDTO.getCommentContent());
        commentPO.setParentId(commentDTO.getParentId());
        commentPO.setPageType(commentDTO.getPageType());
        return commentPO;
    }

    public static CommentVO convert(CommentPO commentPO) {
        if (commentPO == null) {
            return null;
        }
        CommentVO commentVO = new CommentVO();
        commentVO.setId(commentPO.getId());
        commentVO.setArticleId(commentPO.getArticleId());
        commentVO.setUserName(commentPO.getUserName());
        commentVO.setUserEmail(commentPO.getUserEmail());
        commentVO.setUserAvatarUrl(commentPO.getUserAvatarUrl());
        commentVO.setCommentContent(commentPO.getCommentContent());
        commentVO.setParentId(commentPO.getParentId());
        commentVO.setParentTierId(commentPO.getParentTierId());
        commentVO.setUserIp(commentPO.getUserIp());
        commentVO.setUserOs(commentPO.getUserOs());
        commentVO.setUserAgent(commentPO.getUserAgent());
        commentVO.setCreateTime(commentPO.getCreateTime());
        commentVO.setBrowserName(commentPO.getBrowserName());
        commentVO.setPageType(commentPO.getPageType());
        return commentVO;
    }

    public static List<CommentVO> convert(List<CommentPO> commentPOList) {
        if (commentPOList == null) {
            return null;
        }
        List<CommentVO> commentVOList = new ArrayList<>(commentPOList.size());
        for (CommentPO commentPO : commentPOList) {
            if (commentPO == null) {
                continue;
            }
            CommentVO commentVO = new CommentVO();
            commentVO.setId(commentPO.getId());
            commentVO.setArticleId(commentPO.getArticleId());
            commentVO.setUserName(commentPO.getUserName());
            commentVO.setUserEmail(commentPO.getUserEmail());
            commentVO.setUserAvatarUrl(commentPO.getUserAvatarUrl());
            commentVO.setCommentContent(commentPO.getCommentContent());
            commentVO.setParentId(commentPO.getParentId());
            commentVO.setParentTierId(commentPO.getParentTierId());
            commentVO.setUserIp(commentPO.getUserIp());
            commentVO.setUserOs(commentPO.getUserOs());
            commentVO.setUserAgent(commentPO.getUserAgent());
            commentVO.setCreateTime(commentPO.getCreateTime());
            commentVO.setBrowserName(commentPO.getBrowserName());
            commentVO.setPageType(commentPO.getPageType());
            commentVOList.add(commentVO);
        }
        return commentVOList;
    }

}
