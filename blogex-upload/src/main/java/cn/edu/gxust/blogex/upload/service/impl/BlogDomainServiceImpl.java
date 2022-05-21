package cn.edu.gxust.blogex.upload.service.impl;

import cn.edu.gxust.blogex.dao.mappers.BlogSettingMapper;
import cn.edu.gxust.blogex.dao.mappers.BloggerInfoMapper;
import cn.edu.gxust.blogex.upload.service.BlogDomainService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhaoyijie
 * @since 2022/4/30 22:53
 */
@Service
public class BlogDomainServiceImpl implements BlogDomainService {

    @Resource
    private BlogSettingMapper blogSettingMapper;

    @Value(value = "${spring.webflux.base-path}")
    private String serverBasePath;

    @Override
    public String getDomain() {
        return blogSettingMapper.selectOne(null).getWebDomain();
    }

    @Override
    public String getUploadDomainPrefix() {
        return getDomain() + serverBasePath + "/";
    }

}
