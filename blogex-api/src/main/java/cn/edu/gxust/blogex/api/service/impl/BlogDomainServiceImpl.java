package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.service.BlogDomainService;
import cn.edu.gxust.blogex.dao.mappers.BlogSettingMapper;
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

    @Value(value = "${server.servlet.context-path}")
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
