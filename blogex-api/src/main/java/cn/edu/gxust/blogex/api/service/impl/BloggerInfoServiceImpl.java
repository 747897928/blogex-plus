package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.convertor.BloggerInfoConvertor;
import cn.edu.gxust.blogex.api.dto.BloggerInfoDTO;
import cn.edu.gxust.blogex.api.vo.BloggerInfoVO;
import cn.edu.gxust.blogex.dao.mappers.BloggerInfoMapper;
import cn.edu.gxust.blogex.dao.po.BloggerInfoPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.gxust.blogex.api.service.BloggerInfoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 * (BloggerInfo)表服务实现类
 *
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
@CacheConfig(cacheNames = "bloggerInfoCache", cacheManager = "redisCacheManager")
@Service(value = "bloggerInfoServiceImpl")
public class BloggerInfoServiceImpl extends ServiceImpl<BloggerInfoMapper, BloggerInfoPO> implements BloggerInfoService {

    private static final Logger logger = LoggerFactory.getLogger(BloggerInfoServiceImpl.class);

    @Override
    @Cacheable(cacheNames = "bloggerInfo")
    public BloggerInfoVO getBlogInfo() {
        return BloggerInfoConvertor.convert(baseMapper.selectOne(null));
    }

    @Override
    @CacheEvict(cacheNames = "bloggerInfo", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public int update(BloggerInfoDTO bloggerInfoDTO) {
        return baseMapper.updateById(BloggerInfoConvertor.convert(bloggerInfoDTO));
    }

}

