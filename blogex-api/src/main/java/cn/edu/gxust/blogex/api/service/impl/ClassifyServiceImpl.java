package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.convertor.ClassifyConvertor;
import cn.edu.gxust.blogex.api.dto.ClassifyDTO;
import cn.edu.gxust.blogex.api.query.ClassifyPageQuery;
import cn.edu.gxust.blogex.api.dto.UpdateClassifyDTO;
import cn.edu.gxust.blogex.api.service.ArticleService;
import cn.edu.gxust.blogex.api.service.ClassifyService;
import cn.edu.gxust.blogex.api.vo.ClassifyVO;
import cn.edu.gxust.blogex.common.exception.ClassifyNotFoundException;
import cn.edu.gxust.blogex.common.exception.OperationNotAllowedException;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.common.utils.StringUtils;
import cn.edu.gxust.blogex.dao.mappers.ClassifyMapper;
import cn.edu.gxust.blogex.dao.po.ArticlePO;
import cn.edu.gxust.blogex.dao.po.ClassifyPO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/12 12:11
 */
@Service
public class ClassifyServiceImpl extends ServiceImpl<ClassifyMapper, ClassifyPO> implements ClassifyService {

    @Resource
    private ArticleService articleService;

    @Override
    public List<ClassifyVO> selectAll() {
        return ClassifyConvertor.convert(baseMapper.selectAllAndRefCount());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(ClassifyDTO classifyDTO) {
        ClassifyPO classifyPO = ClassifyConvertor.convert(classifyDTO);
        classifyPO.setCreateTime(new Date());
        classifyPO.setUpdateTime(new Date());
        return baseMapper.insert(classifyPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(UpdateClassifyDTO updateClassifyDTO) {
        ClassifyPO classifyPO = checkExist(updateClassifyDTO.getId());
        classifyPO.setClassifyName(updateClassifyDTO.getNewClassifyName());
        int count = baseMapper.updateById(classifyPO);
        //更新文章表里分类名这个冗余的字段
        count = count + articleService.updateClassifyName(classifyPO.getId());
        return count;
    }

    public ClassifyPO selectById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ClassifyPO checkExist(Integer id) {
        ClassifyPO classifyPO = baseMapper.selectById(id);
        if (null == classifyPO) {
            throw new ClassifyNotFoundException(id);
        }
        return classifyPO;
    }

    @Override
    public Pagination<ClassifyVO> listPage(ClassifyPageQuery classifyQuery) {
        Long pageNo = classifyQuery.getPageNo();
        Long pageSize = classifyQuery.getPageSize();
        Page<ClassifyPO> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<ClassifyPO> wrapper = Wrappers.<ClassifyPO>lambdaQuery();
        String searchKey = classifyQuery.getSearchKey();
        if (StringUtils.isNotBlank(searchKey)) {
            wrapper.like(ClassifyPO::getClassifyName, searchKey).or().like(ClassifyPO::getId, searchKey);
        }
        Page<ClassifyPO> selectPage = baseMapper.selectPage(page, wrapper);
        List<ClassifyPO> records = selectPage.getRecords();
        List<ClassifyVO> resultList = ClassifyConvertor.convert(records);
        return new Pagination<>(selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal(), resultList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<Integer> idList) {
        List<ArticlePO> articlePOList = articleService.getBaseMapper().selectList(
                Wrappers.<ArticlePO>lambdaQuery().select(ArticlePO::getTitle, ArticlePO::getClassifyName)
                        .in(ArticlePO::getClassifyId, idList)
        );
        if (articlePOList.size() == 0) {
            Wrapper<ClassifyPO> wrapper = Wrappers.<ClassifyPO>lambdaUpdate().in(ClassifyPO::getId, idList);
            return baseMapper.delete(wrapper);
        } else {
            StringBuilder sb = new StringBuilder();
            for (ArticlePO articlePO : articlePOList) {
                sb.append("[").append(articlePO.getTitle()).append("->").append(articlePO.getClassifyName()).append("]");
            }
            throw new OperationNotAllowedException("存在文章引用分类:" + sb.toString());
        }
    }

    @Override
    public ClassifyVO detail(Integer id) {
        ClassifyPO classifyPO = checkExist(id);
        return ClassifyConvertor.convert(classifyPO);
    }

}
