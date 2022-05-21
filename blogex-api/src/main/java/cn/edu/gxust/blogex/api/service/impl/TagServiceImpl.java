package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.convertor.TagConvertor;
import cn.edu.gxust.blogex.api.dto.TagDTO;
import cn.edu.gxust.blogex.api.query.TagPageQuery;
import cn.edu.gxust.blogex.api.dto.UpdateTagDTO;
import cn.edu.gxust.blogex.api.service.ArticleTagMapService;
import cn.edu.gxust.blogex.api.service.TagService;
import cn.edu.gxust.blogex.api.vo.TagVO;
import cn.edu.gxust.blogex.common.exception.TagNotFoundException;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.common.utils.StringUtils;
import cn.edu.gxust.blogex.dao.mappers.TagMapper;
import cn.edu.gxust.blogex.dao.po.TagPO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoyijie
 * @since 2022/3/12 13:32
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagPO> implements TagService {

    @Resource
    private ArticleTagMapService articleTagMapService;

    @Override
    public List<TagVO> getByArticleId(Integer articleId) {
        List<Integer> tagIdList = articleTagMapService.getByArticleId(articleId);
        List<TagPO> tagPOList = baseMapper.selectList(Wrappers.<TagPO>lambdaQuery().in(TagPO::getId, tagIdList));
        return TagConvertor.convert(tagPOList);
    }

    @Override
    public Map<Integer, List<TagPO>> getByArticleIdList(List<Integer> articleIdList) {
        List<TagPO> tagPOList = baseMapper.getByArticleIdList(articleIdList);
        Map<Integer, List<TagPO>> map = new HashMap<>();
        for (TagPO tagPO : tagPOList) {
            Integer articleId = tagPO.getArticleId();
            if (map.containsKey(articleId)) {
                map.get(articleId).add(tagPO);
            } else {
                map.put(articleId, new ArrayList<TagPO>() {
                    {
                        add(tagPO);
                    }
                });
            }
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TagDTO tagDTO) {
        TagPO tagPO = TagConvertor.convert(tagDTO);
        tagPO.setCreateTime(new Date());
        tagPO.setUpdateTime(new Date());
        return baseMapper.insert(tagPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<Integer> idList) {
        Wrapper<TagPO> wrapper = Wrappers.<TagPO>lambdaUpdate().in(TagPO::getId, idList);
        return baseMapper.delete(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(UpdateTagDTO updateTagDTO) {
        TagPO tagPO = checkExist(updateTagDTO.getId());
        tagPO.setTagName(updateTagDTO.getNewTagName());
        return baseMapper.updateById(tagPO);
    }

    @Override
    public TagPO checkExist(Integer id) {
        TagPO tagPO = baseMapper.selectById(id);
        if (null == tagPO) {
            throw new TagNotFoundException(id);
        }
        return tagPO;
    }

    @Override
    public Pagination<TagVO> listPage(TagPageQuery tagQuery) {
        Long pageNo = tagQuery.getPageNo();
        Long pageSize = tagQuery.getPageSize();
        Page<TagPO> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<TagPO> wrapper = Wrappers.<TagPO>lambdaQuery();
        String searchKey = tagQuery.getSearchKey();
        if (StringUtils.isNotBlank(searchKey)) {
            wrapper.like(TagPO::getTagName, searchKey).or().like(TagPO::getId, searchKey);
        }
        Page<TagPO> selectPage = baseMapper.selectPage(page, wrapper);
        List<TagPO> records = selectPage.getRecords();
        List<TagVO> resultList = TagConvertor.convert(records);
        return new Pagination<>(selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal(), resultList);

    }

    @Override
    public List<TagVO> queryAll() {
        return TagConvertor.convert(baseMapper.selectAllAndRefCount());
    }

    @Override
    public TagVO detail(Integer id) {
        return TagConvertor.convert(getById(id));
    }

}
