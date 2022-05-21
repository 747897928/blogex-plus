package cn.edu.gxust.blogex.common.service.impl;

import cn.edu.gxust.blogex.common.service.UniqueIDService;

import java.util.UUID;

/**
 * uuid唯一id生成器，请注意，这里没有@Service注解，如果需要使用请用@Bean注入
 *
 * @author zhaoyijie
 * @since 2022/4/1 09:27
 */
public class UuIdUniqueIDServiceImpl implements UniqueIDService {

    @Override
    public String createStringUniqueId() {
        return UUID.randomUUID().toString();
    }

}
