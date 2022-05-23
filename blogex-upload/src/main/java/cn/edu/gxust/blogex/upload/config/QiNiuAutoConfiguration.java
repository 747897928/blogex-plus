package cn.edu.gxust.blogex.upload.config;

import cn.edu.gxust.blogex.upload.entity.QiNiuProperties;
import cn.edu.gxust.blogex.upload.service.UploadFileService;
import cn.edu.gxust.blogex.upload.service.impl.QiNiuUploadFileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云自动配置类
 *
 * @author zhaoyijie
 * @since 2022/5/22 16:08
 */
//@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureBefore(value = LocalUploadAutoConfiguration.class)
@Configuration
@EnableConfigurationProperties(value = QiNiuProperties.class)
@ConditionalOnClass(value = UploadFileService.class)
public class QiNiuAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(QiNiuAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(value = UploadFileService.class)
    @ConditionalOnProperty(prefix = "qiniu", name = "enable", havingValue = "true")
    public UploadFileService qiNiuUploadFileServiceImpl(QiNiuProperties qiNiuProperties) {
        logger.info("QiNiuUploadFileServiceImpl matched");
        return new QiNiuUploadFileServiceImpl(qiNiuProperties);
    }

}
