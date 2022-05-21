package cn.edu.gxust.blogex.upload.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * 为swagger-ui注册一个webflux资源处理程序
 *
 * @author zhaoyijie
 * @since 2022/4/3 21:51
 */
@Configuration
public class WebfluxConfig implements WebFluxConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("uploadsFile/**")
                .addResourceLocations("file:uploadsFile/");

        registry.addResourceHandler("blogex-ui/**")
                .addResourceLocations("file:blogex-ui/");
    }

}
