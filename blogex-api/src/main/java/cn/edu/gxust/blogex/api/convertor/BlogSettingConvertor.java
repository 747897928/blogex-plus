package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.dto.BlogSettingDTO;
import cn.edu.gxust.blogex.api.vo.BlogSettingVO;
import cn.edu.gxust.blogex.dao.po.BlogSettingPO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022-05-15 12:40:54
 */
public class BlogSettingConvertor {

    private BlogSettingConvertor() {
        throw new IllegalStateException("Construct BlogSettingConvertor");
    }

    public static BlogSettingPO convert(BlogSettingDTO blogSettingDTO) {
        if (blogSettingDTO == null) {
            return null;
        }
        BlogSettingPO blogSettingPO = new BlogSettingPO();
        blogSettingPO.setId(blogSettingDTO.getId());
        blogSettingPO.setWebDomain(blogSettingDTO.getWebDomain());
        blogSettingPO.setMailEnable(blogSettingDTO.getMailEnable());
        blogSettingPO.setBlogAddress(blogSettingDTO.getBlogAddress());
        blogSettingPO.setBlogMailAddress(blogSettingDTO.getBlogMailAddress());
        blogSettingPO.setCreateTime(blogSettingDTO.getCreateTime());
        blogSettingPO.setUpdateTime(blogSettingDTO.getUpdateTime());
        return blogSettingPO;
    }

    public static BlogSettingVO convert(BlogSettingPO blogSettingPO) {
        if (blogSettingPO == null) {
            return null;
        }
        BlogSettingVO blogSettingVO = new BlogSettingVO();
        blogSettingVO.setId(blogSettingPO.getId());
        blogSettingVO.setWebDomain(blogSettingPO.getWebDomain());
        blogSettingVO.setMailEnable(blogSettingPO.getMailEnable());
        blogSettingVO.setBlogAddress(blogSettingPO.getBlogAddress());
        blogSettingVO.setBlogMailAddress(blogSettingPO.getBlogMailAddress());
        blogSettingVO.setCreateTime(blogSettingPO.getCreateTime());
        blogSettingVO.setUpdateTime(blogSettingPO.getUpdateTime());
        return blogSettingVO;
    }

}

