package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.dto.BlogLinkDTO;
import cn.edu.gxust.blogex.api.vo.BlogLinkVO;
import cn.edu.gxust.blogex.dao.po.BlogLinkPO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
public class BlogLinkConvertor {

    private BlogLinkConvertor() {
        throw new IllegalStateException("Construct BlogLinkConvertor");
    }

    public static BlogLinkPO convert(BlogLinkDTO blogLinkDTO) {
        if (blogLinkDTO == null) {
            return null;
        }
        BlogLinkPO blogLinkPO = new BlogLinkPO();
        blogLinkPO.setId(blogLinkDTO.getId());
        blogLinkPO.setLinkName(blogLinkDTO.getLinkName());
        blogLinkPO.setLinkHref(blogLinkDTO.getLinkHref());
        blogLinkPO.setLinkAvatarUrl(blogLinkDTO.getLinkAvatarUrl());
        blogLinkPO.setDescription(blogLinkDTO.getDescription());
        return blogLinkPO;
    }

    public static BlogLinkVO convert(BlogLinkPO blogLinkPO) {
        if (blogLinkPO == null) {
            return null;
        }
        BlogLinkVO blogLinkVO = new BlogLinkVO();
        blogLinkVO.setId(blogLinkPO.getId());
        blogLinkVO.setLinkName(blogLinkPO.getLinkName());
        blogLinkVO.setLinkHref(blogLinkPO.getLinkHref());
        blogLinkVO.setLinkAvatarUrl(blogLinkPO.getLinkAvatarUrl());
        blogLinkVO.setDescription(blogLinkPO.getDescription());
        blogLinkVO.setCreateTime(blogLinkPO.getCreateTime());
        blogLinkVO.setUpdateTime(blogLinkPO.getUpdateTime());
        return blogLinkVO;
    }

    public static List<BlogLinkVO> convert(List<BlogLinkPO> blogLinkPOList) {
        if (blogLinkPOList == null) {
            return null;
        }
        List<BlogLinkVO> blogLinkVOList = new ArrayList<>(blogLinkPOList.size());
        for (BlogLinkPO blogLinkPO : blogLinkPOList) {
            if (blogLinkPO == null) {
                continue;
            }
            BlogLinkVO blogLinkVO = new BlogLinkVO();
            blogLinkVO.setId(blogLinkPO.getId());
            blogLinkVO.setLinkName(blogLinkPO.getLinkName());
            blogLinkVO.setLinkHref(blogLinkPO.getLinkHref());
            blogLinkVO.setLinkAvatarUrl(blogLinkPO.getLinkAvatarUrl());
            blogLinkVO.setDescription(blogLinkPO.getDescription());
            blogLinkVO.setCreateTime(blogLinkPO.getCreateTime());
            blogLinkVO.setUpdateTime(blogLinkPO.getUpdateTime());
            blogLinkVOList.add(blogLinkVO);
        }
        return blogLinkVOList;
    }

}

