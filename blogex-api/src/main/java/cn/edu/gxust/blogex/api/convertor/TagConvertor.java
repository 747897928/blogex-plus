package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.dto.TagDTO;
import cn.edu.gxust.blogex.api.vo.TagVO;
import cn.edu.gxust.blogex.dao.po.TagPO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/12 13:47
 */
public class TagConvertor {

    private TagConvertor() {
        throw new IllegalStateException("Construct TagConvertor");
    }

    public static TagPO convert(TagDTO tagDTO) {
        if (tagDTO == null) {
            return null;
        }
        TagPO tagPO = new TagPO();
        tagPO.setId(tagDTO.getId());
        tagPO.setTagName(tagDTO.getTagName());
        tagPO.setCreateTime(tagDTO.getCreateTime());
        tagPO.setUpdateTime(tagDTO.getUpdateTime());
        return tagPO;
    }

    public static TagVO convert(TagPO tagPO) {
        if (tagPO == null) {
            return null;
        }
        TagVO tagVO = new TagVO();
        tagVO.setId(tagPO.getId());
        tagVO.setTagName(tagPO.getTagName());
        tagVO.setRefCount(tagPO.getRefCount());
        tagVO.setCreateTime(tagPO.getCreateTime());
        tagVO.setUpdateTime(tagPO.getUpdateTime());
        return tagVO;
    }

    public static List<TagVO> convert(List<TagPO> tagPOList) {
        if (tagPOList == null) {
            return null;
        }
        List<TagVO> tagVOList = new ArrayList<>(tagPOList.size());
        for (TagPO tagPO : tagPOList) {
            if (tagPO == null) {
                continue;
            }
            TagVO tagVO = new TagVO();
            tagVO.setId(tagPO.getId());
            tagVO.setTagName(tagPO.getTagName());
            tagVO.setRefCount(tagPO.getRefCount());
            tagVO.setCreateTime(tagPO.getCreateTime());
            tagVO.setUpdateTime(tagPO.getUpdateTime());
            tagVOList.add(tagVO);
        }
        return tagVOList;
    }
}
