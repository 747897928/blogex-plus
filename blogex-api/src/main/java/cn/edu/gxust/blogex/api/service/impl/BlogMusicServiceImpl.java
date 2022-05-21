package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.convertor.BlogMusicConvertor;
import cn.edu.gxust.blogex.api.dto.BlogMusicDTO;
import cn.edu.gxust.blogex.api.query.BlogMusicPageQuery;
import cn.edu.gxust.blogex.api.vo.BlogMusicVO;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.common.utils.StringUtils;
import cn.edu.gxust.blogex.dao.mappers.BlogMusicMapper;
import cn.edu.gxust.blogex.dao.po.BlogMusicPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.gxust.blogex.api.service.BlogMusicService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * (BlogMusic)表服务实现类
 *
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
@CacheConfig(cacheNames = "bloggerMusicCache", cacheManager = "redisCacheManager")
@Service(value = "blogMusicServiceImpl")
public class BlogMusicServiceImpl extends ServiceImpl<BlogMusicMapper, BlogMusicPO> implements BlogMusicService {

    private static final Logger logger = LoggerFactory.getLogger(BlogMusicServiceImpl.class);

    @Override
    @CacheEvict(cacheNames = "bloggerMusic", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public int insert(BlogMusicDTO blogMusicDTO) {
        BlogMusicPO blogMusicPO = BlogMusicConvertor.convert(blogMusicDTO);
        blogMusicPO.setCreateTime(new Date());
        blogMusicPO.setUpdateTime(new Date());
        return baseMapper.insert(blogMusicPO);
    }

    @Override
    public Pagination<BlogMusicVO> listPage(BlogMusicPageQuery query) {
        Long pageNo = query.getPageNo();
        Long pageSize = query.getPageSize();
        Page<BlogMusicPO> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<BlogMusicPO> wrapper = Wrappers.<BlogMusicPO>lambdaQuery();
        String searchKey = query.getSearchKey();
        if (null != query.getStartTime()) {
            wrapper.ge(BlogMusicPO::getCreateTime, query.getStartTime());
        }
        if (null != query.getEndTime()) {
            wrapper.le(BlogMusicPO::getCreateTime, query.getEndTime());
        }
        if (StringUtils.isNotBlank(searchKey)) {
            wrapper.like(BlogMusicPO::getMusicName, searchKey)
                    .or().like(BlogMusicPO::getId, searchKey)
                    .or().like(BlogMusicPO::getMusicArtist, searchKey);
        }
        wrapper.orderByDesc(BlogMusicPO::getCreateTime);
        wrapper.orderByAsc(BlogMusicPO::getPriority);
        Page<BlogMusicPO> selectPage = baseMapper.selectPage(page, wrapper);
        List<BlogMusicPO> records = selectPage.getRecords();
        List<BlogMusicVO> resultList = BlogMusicConvertor.convert(records);
        return new Pagination<>(selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal(), resultList);

    }

    @Override
    @Cacheable(cacheNames = "bloggerMusic")
    public List<BlogMusicVO> selectAll() {
        LambdaQueryWrapper<BlogMusicPO> wrapper = Wrappers.<BlogMusicPO>lambdaQuery();
        wrapper.orderByAsc(BlogMusicPO::getPriority);
        wrapper.orderByDesc(BlogMusicPO::getCreateTime);
        List<BlogMusicPO> blogMusicPOList = list(wrapper);
        return BlogMusicConvertor.convert(blogMusicPOList);
    }

    @Override
    @CacheEvict(cacheNames = "bloggerMusic", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<Integer> idList) {
        return baseMapper.deleteBatchIds(idList);
    }

    @Override
    @CacheEvict(cacheNames = "bloggerMusic", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public int update(BlogMusicDTO blogMusicDTO) {
        BlogMusicPO blogMusicPO = BlogMusicConvertor.convert(blogMusicDTO);
        return baseMapper.updateById(blogMusicPO);
    }

}

