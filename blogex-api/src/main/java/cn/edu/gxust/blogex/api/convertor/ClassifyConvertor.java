package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.dto.ClassifyDTO;
import cn.edu.gxust.blogex.api.vo.ClassifyVO;
import cn.edu.gxust.blogex.dao.po.ClassifyPO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/12 13:20
 */
public class ClassifyConvertor {

    private ClassifyConvertor() {
        throw new IllegalStateException("Construct ClassifyConvertor");
    }

    public static ClassifyPO convert(ClassifyDTO classifyDTO) {
        if (classifyDTO == null) {
            return null;
        }
        ClassifyPO classifyPO = new ClassifyPO();
        classifyPO.setId(classifyDTO.getId());
        classifyPO.setClassifyName(classifyDTO.getClassifyName());
        return classifyPO;
    }

    public static ClassifyVO convert(ClassifyPO classifyPO) {
        if (classifyPO == null) {
            return null;
        }
        ClassifyVO classifyVO = new ClassifyVO();
        classifyVO.setId(classifyPO.getId());
        classifyVO.setRefCount(classifyPO.getRefCount());
        classifyVO.setClassifyName(classifyPO.getClassifyName());
        classifyVO.setCreateTime(classifyPO.getCreateTime());
        classifyVO.setUpdateTime(classifyPO.getUpdateTime());
        return classifyVO;
    }

    public static List<ClassifyVO> convert(List<ClassifyPO> classifyPOList) {
        if (classifyPOList == null) {
            return null;
        }
        List<ClassifyVO> classifyVOList = new ArrayList<>(classifyPOList.size());
        for (ClassifyPO classifyPO : classifyPOList) {
            if (classifyPO == null) {
                continue;
            }
            ClassifyVO classifyVO = new ClassifyVO();
            classifyVO.setId(classifyPO.getId());
            classifyVO.setClassifyName(classifyPO.getClassifyName());
            classifyVO.setRefCount(classifyPO.getRefCount());
            classifyVO.setCreateTime(classifyPO.getCreateTime());
            classifyVO.setUpdateTime(classifyPO.getUpdateTime());
            classifyVOList.add(classifyVO);
        }
        return classifyVOList;
    }

}
