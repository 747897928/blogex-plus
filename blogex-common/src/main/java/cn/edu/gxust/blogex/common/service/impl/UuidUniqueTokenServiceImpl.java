package cn.edu.gxust.blogex.common.service.impl;

import cn.edu.gxust.blogex.common.service.UniqueTokenService;

import java.util.UUID;

/**
 *
 * @author zhaoyijie
 * @since 2022/4/1 09:57
 */
public class UuidUniqueTokenServiceImpl implements UniqueTokenService {

    @Override
    public String createUniqueToken() {
        return UUID.randomUUID().toString();
    }

}
