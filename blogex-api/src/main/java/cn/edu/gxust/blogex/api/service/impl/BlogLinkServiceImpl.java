package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.convertor.BlogLinkConvertor;
import cn.edu.gxust.blogex.api.dto.BlogLinkDTO;
import cn.edu.gxust.blogex.api.query.BlogLinkPageQuery;
import cn.edu.gxust.blogex.api.vo.BlogLinkVO;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.common.utils.StringUtils;
import cn.edu.gxust.blogex.dao.mappers.BlogLinkMapper;
import cn.edu.gxust.blogex.dao.po.BlogLinkPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.gxust.blogex.api.service.BlogLinkService;
import org.springframework.stereotype.Service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * (BlogLink)表服务实现类
 *
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
@Service(value = "blogLinkServiceImpl")
public class BlogLinkServiceImpl extends ServiceImpl<BlogLinkMapper, BlogLinkPO> implements BlogLinkService {

    private static final Logger logger = LoggerFactory.getLogger(BlogLinkServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(BlogLinkDTO blogLinkDTO) {
        BlogLinkPO blogLinkPO = queryByLinkName(blogLinkDTO.getLinkName());
        if (null != blogLinkPO) {
            throw new IllegalArgumentException("重复的友联名：" + blogLinkPO.getLinkName());
        }
        BlogLinkPO linkPO = BlogLinkConvertor.convert(blogLinkDTO);
        linkPO.setCreateTime(new Date());
        linkPO.setUpdateTime(new Date());
        return baseMapper.insert(linkPO);
    }

    @Override
    public Pagination<BlogLinkVO> listPage(BlogLinkPageQuery query) {
        Long pageNo = query.getPageNo();
        Long pageSize = query.getPageSize();
        Page<BlogLinkPO> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<BlogLinkPO> wrapper = Wrappers.<BlogLinkPO>lambdaQuery();
        String searchKey = query.getSearchKey();
        if (null != query.getStartTime()) {
            wrapper.ge(BlogLinkPO::getCreateTime, query.getStartTime());
        }
        if (null != query.getEndTime()) {
            wrapper.le(BlogLinkPO::getCreateTime, query.getEndTime());
        }
        if (StringUtils.isNotBlank(searchKey)) {
            wrapper.like(BlogLinkPO::getLinkName, searchKey)
                    .or().like(BlogLinkPO::getId, searchKey)
                    .or().like(BlogLinkPO::getLinkHref, searchKey);
        }
        wrapper.orderByAsc(BlogLinkPO::getCreateTime);
        Page<BlogLinkPO> selectPage = baseMapper.selectPage(page, wrapper);
        List<BlogLinkPO> records = selectPage.getRecords();
        List<BlogLinkVO> resultList = BlogLinkConvertor.convert(records);
        return new Pagination<>(selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal(), resultList);
    }

    @Override
    public List<BlogLinkVO> selectAll() {
        List<BlogLinkPO> blogLinkPOList = baseMapper.selectList(null);
        return BlogLinkConvertor.convert(blogLinkPOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<Integer> idList) {
        return baseMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(BlogLinkDTO blogLinkDTO) {
        BlogLinkPO blogLinkPO = BlogLinkConvertor.convert(blogLinkDTO);
        return baseMapper.updateById(blogLinkPO);
    }

    @Override
    public BlogLinkPO queryByLinkName(String linkName) {
        return baseMapper.selectOne(Wrappers.<BlogLinkPO>lambdaQuery().eq(BlogLinkPO::getLinkName, linkName));
    }

}

