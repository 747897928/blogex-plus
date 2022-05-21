package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.dto.BlogMusicDTO;
import cn.edu.gxust.blogex.api.vo.BlogMusicVO;
import cn.edu.gxust.blogex.dao.po.BlogMusicPO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
public class BlogMusicConvertor {

    private BlogMusicConvertor() {
        throw new IllegalStateException("Construct BlogMusicConvertor");
    }

    public static BlogMusicPO convert(BlogMusicDTO blogMusicDTO) {
        if (blogMusicDTO == null) {
            return null;
        }
        BlogMusicPO blogMusicPO = new BlogMusicPO();
        blogMusicPO.setId(blogMusicDTO.getId());
        blogMusicPO.setMusicName(blogMusicDTO.getMusicName());
        blogMusicPO.setMusicArtist(blogMusicDTO.getMusicArtist());
        blogMusicPO.setMusicUrl(blogMusicDTO.getMusicUrl());
        blogMusicPO.setMusicLrc(blogMusicDTO.getMusicLrc());
        blogMusicPO.setMusicCover(blogMusicDTO.getMusicCover());
        blogMusicPO.setPriority(blogMusicDTO.getPriority());
        blogMusicPO.setCreateTime(blogMusicDTO.getCreateTime());
        blogMusicPO.setUpdateTime(blogMusicDTO.getUpdateTime());
        return blogMusicPO;
    }

    public static BlogMusicVO convert(BlogMusicPO blogMusicPO) {
        if (blogMusicPO == null) {
            return null;
        }
        BlogMusicVO blogMusicVO = new BlogMusicVO();
        blogMusicVO.setId(blogMusicPO.getId());
        blogMusicVO.setMusicName(blogMusicPO.getMusicName());
        blogMusicVO.setMusicArtist(blogMusicPO.getMusicArtist());
        blogMusicVO.setMusicUrl(blogMusicPO.getMusicUrl());
        blogMusicVO.setMusicLrc(blogMusicPO.getMusicLrc());
        blogMusicVO.setMusicCover(blogMusicPO.getMusicCover());
        blogMusicVO.setPriority(blogMusicPO.getPriority());
        blogMusicVO.setCreateTime(blogMusicPO.getCreateTime());
        blogMusicVO.setUpdateTime(blogMusicPO.getUpdateTime());
        return blogMusicVO;
    }

    public static List<BlogMusicVO> convert(List<BlogMusicPO> blogMusicPOList) {
        if (blogMusicPOList == null) {
            return null;
        }
        List<BlogMusicVO> blogMusicVOList = new ArrayList<>(blogMusicPOList.size());
        for (BlogMusicPO blogMusicPO : blogMusicPOList) {
            if (blogMusicPO == null) {
                continue;
            }
            BlogMusicVO blogMusicVO = new BlogMusicVO();
            blogMusicVO.setId(blogMusicPO.getId());
            blogMusicVO.setMusicName(blogMusicPO.getMusicName());
            blogMusicVO.setMusicArtist(blogMusicPO.getMusicArtist());
            blogMusicVO.setMusicUrl(blogMusicPO.getMusicUrl());
            blogMusicVO.setMusicLrc(blogMusicPO.getMusicLrc());
            blogMusicVO.setMusicCover(blogMusicPO.getMusicCover());
            blogMusicVO.setPriority(blogMusicPO.getPriority());
            blogMusicVO.setCreateTime(blogMusicPO.getCreateTime());
            blogMusicVO.setUpdateTime(blogMusicPO.getUpdateTime());
            blogMusicVOList.add(blogMusicVO);
        }
        return blogMusicVOList;
    }

}

