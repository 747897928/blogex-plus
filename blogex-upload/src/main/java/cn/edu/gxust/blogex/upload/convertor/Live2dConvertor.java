package cn.edu.gxust.blogex.upload.convertor;

import cn.edu.gxust.blogex.dao.po.Live2dPO;
import cn.edu.gxust.blogex.upload.dto.Live2dDTO;
import cn.edu.gxust.blogex.upload.vo.Live2dVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/18 22:53
 */
public class Live2dConvertor {

    private Live2dConvertor() {
        throw new IllegalStateException("Construct Live2dConvertor");
    }

    public static Live2dPO convert(Live2dDTO live2dDTO) {
        if (live2dDTO == null) {
            return null;
        }
        Live2dPO live2dPO = new Live2dPO();
        live2dPO.setId(live2dDTO.getId());
        live2dPO.setModelName(live2dDTO.getModelName());
        live2dPO.setModelJsonPath(live2dDTO.getModelJsonPath());
        live2dPO.setModelImagePath(live2dDTO.getModelImagePath());
        live2dPO.setBackgroundPath(live2dDTO.getBackgroundPath());
        live2dPO.setX(live2dDTO.getX());
        live2dPO.setY(live2dDTO.getY());
        live2dPO.setScale(live2dDTO.getScale());
        live2dPO.setWidth(live2dDTO.getWidth());
        live2dPO.setHeight(live2dDTO.getHeight());
        live2dPO.setAnchorx(live2dDTO.getAnchorx());
        live2dPO.setAnchory(live2dDTO.getAnchory());
        live2dPO.setBaseFilePath(live2dDTO.getBaseFilePath());
        live2dPO.setShowMark(live2dDTO.getShowMark());
        live2dPO.setDescription(live2dDTO.getDescription());
        return live2dPO;
    }

    public static Live2dVO convert(Live2dPO live2dPO) {
        if (live2dPO == null) {
            return null;
        }
        Live2dVO live2dVO = new Live2dVO();
        live2dVO.setId(live2dPO.getId());
        live2dVO.setModelName(live2dPO.getModelName());
        live2dVO.setModelJsonPath(live2dPO.getModelJsonPath());
        live2dVO.setModelImagePath(live2dPO.getModelImagePath());
        live2dVO.setBackgroundPath(live2dPO.getBackgroundPath());
        live2dVO.setX(live2dPO.getX());
        live2dVO.setY(live2dPO.getY());
        live2dVO.setScale(live2dPO.getScale());
        live2dVO.setWidth(live2dPO.getWidth());
        live2dVO.setHeight(live2dPO.getHeight());
        live2dVO.setAnchorx(live2dPO.getAnchorx());
        live2dVO.setAnchory(live2dPO.getAnchory());
        live2dVO.setBaseFilePath(live2dPO.getBaseFilePath());
        live2dVO.setShowMark(live2dPO.getShowMark());
        live2dVO.setDescription(live2dPO.getDescription());
        live2dVO.setCreateTime(live2dPO.getCreateTime());
        live2dVO.setUpdateTime(live2dPO.getUpdateTime());
        return live2dVO;
    }

    public static List<Live2dVO> convert(List<Live2dPO> live2dPOList) {
        if (live2dPOList == null) {
            return null;
        }
        List<Live2dVO> live2dVOList = new ArrayList<>(live2dPOList.size());
        for (Live2dPO live2dPO : live2dPOList) {
            if (live2dPO == null) {
                continue;
            }
            Live2dVO live2dVO = new Live2dVO();
            live2dVO.setId(live2dPO.getId());
            live2dVO.setModelName(live2dPO.getModelName());
            live2dVO.setModelJsonPath(live2dPO.getModelJsonPath());
            live2dVO.setModelImagePath(live2dPO.getModelImagePath());
            live2dVO.setBackgroundPath(live2dPO.getBackgroundPath());
            live2dVO.setX(live2dPO.getX());
            live2dVO.setY(live2dPO.getY());
            live2dVO.setScale(live2dPO.getScale());
            live2dVO.setWidth(live2dPO.getWidth());
            live2dVO.setHeight(live2dPO.getHeight());
            live2dVO.setAnchorx(live2dPO.getAnchorx());
            live2dVO.setAnchory(live2dPO.getAnchory());
            live2dVO.setShowMark(live2dPO.getShowMark());
            live2dVO.setBaseFilePath(live2dPO.getBaseFilePath());
            live2dVO.setDescription(live2dPO.getDescription());
            live2dVO.setCreateTime(live2dPO.getCreateTime());
            live2dVO.setUpdateTime(live2dPO.getUpdateTime());
            live2dVOList.add(live2dVO);
        }
        return live2dVOList;
    }

}
