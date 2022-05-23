package cn.edu.gxust.blogex.upload.config;

import cn.edu.gxust.blogex.upload.service.BlogDomainService;
import cn.edu.gxust.blogex.upload.service.UploadFileService;
import cn.edu.gxust.blogex.upload.service.impl.LocalUploadFileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 本地上传的自动配置类，当其他类型的上传配置类生效的时候，
 * 这个配置类将不会往容器注入bean对象，说白了就是兜底配置类
 *
 * @author zhaoyijie
 * @since 2022/5/22 16:41
 */
@Configuration
@ConditionalOnClass(value = UploadFileService.class)
public class LocalUploadAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(LocalUploadAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(value = UploadFileService.class)
    public UploadFileService localUploadFileServiceImpl(BlogDomainService blogDomainService) {
        logger.info("LocalUploadFileServiceImpl matched");
        return new LocalUploadFileServiceImpl(blogDomainService);
    }

}
