package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.convertor.BlogSettingConvertor;
import cn.edu.gxust.blogex.api.dto.BlogSettingDTO;
import cn.edu.gxust.blogex.api.service.BlogSettingService;
import cn.edu.gxust.blogex.api.vo.BlogSettingVO;
import cn.edu.gxust.blogex.dao.mappers.BlogSettingMapper;
import cn.edu.gxust.blogex.dao.po.BlogSettingPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * (BlogSetting)表服务实现类
 *
 * @author zhaoyijie
 * @since 2022-05-15 12:40:54
 */
@Service(value = "blogSettingServiceImpl")
public class BlogSettingServiceImpl extends ServiceImpl<BlogSettingMapper, BlogSettingPO> implements BlogSettingService {

    private static final Logger logger = LoggerFactory.getLogger(BlogSettingServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(BlogSettingDTO blogSettingDTO) {
        BlogSettingPO blogSettingPO = BlogSettingConvertor.convert(blogSettingDTO);
        blogSettingPO.setUpdateTime(new Date());
        return baseMapper.updateById(blogSettingPO);
    }

    @Override
    public BlogSettingVO getBlogSetting() {
        return BlogSettingConvertor.convert(baseMapper.selectOne(null));
    }
}

