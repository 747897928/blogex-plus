package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.dto.BloggerInfoDTO;
import cn.edu.gxust.blogex.api.vo.BloggerInfoVO;
import cn.edu.gxust.blogex.dao.po.BloggerInfoPO;

/**
 * @author zhaoyijie
 * @since 2022-03-20 17:11:51
 */
public class BloggerInfoConvertor {

    private BloggerInfoConvertor() {
        throw new IllegalStateException("Construct BloggerInfoConvertor");
    }

    public static BloggerInfoPO convert(BloggerInfoDTO bloggerInfoDTO) {
        if (bloggerInfoDTO == null) {
            return null;
        }
        BloggerInfoPO bloggerInfoPO = new BloggerInfoPO();
        bloggerInfoPO.setId(bloggerInfoDTO.getId());
        bloggerInfoPO.setUserName(bloggerInfoDTO.getUserName());
        bloggerInfoPO.setAvatarUrl(bloggerInfoDTO.getAvatarUrl());
        bloggerInfoPO.setSignature(bloggerInfoDTO.getSignature());
        bloggerInfoPO.setGithubUrl(bloggerInfoDTO.getGithubUrl());
        bloggerInfoPO.setRecordNumber(bloggerInfoDTO.getRecordNumber());
        bloggerInfoPO.setBloggerDetail(bloggerInfoDTO.getBloggerDetail());
        return bloggerInfoPO;
    }

    public static BloggerInfoVO convert(BloggerInfoPO bloggerInfoPO) {
        if (bloggerInfoPO == null) {
            return null;
        }
        BloggerInfoVO bloggerInfoVO = new BloggerInfoVO();
        bloggerInfoVO.setId(bloggerInfoPO.getId());
        bloggerInfoVO.setUserName(bloggerInfoPO.getUserName());
        bloggerInfoVO.setAvatarUrl(bloggerInfoPO.getAvatarUrl());
        bloggerInfoVO.setSignature(bloggerInfoPO.getSignature());
        bloggerInfoVO.setGithubUrl(bloggerInfoPO.getGithubUrl());
        bloggerInfoVO.setRecordNumber(bloggerInfoPO.getRecordNumber());
        bloggerInfoVO.setBloggerDetail(bloggerInfoPO.getBloggerDetail());
        bloggerInfoVO.setCreateTime(bloggerInfoPO.getCreateTime());
        bloggerInfoVO.setUpdateTime(bloggerInfoPO.getUpdateTime());
        return bloggerInfoVO;
    }

}

