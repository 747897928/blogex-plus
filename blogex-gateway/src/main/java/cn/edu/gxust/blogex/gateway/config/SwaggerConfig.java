package cn.edu.gxust.blogex.gateway.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableKnife4j
@EnableSwagger2
//@RefreshScope//只需要在需要动态读取配置的类上添加此注解就可以
public class SwaggerConfig {

    @Value("${blogex.swagger.show:false}")
    private Boolean swaggerShow;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("cn.edu.gxust.blogex.gateway.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("BlogEx gateWay")
                .contact(new Contact("水瓶座鬼才", "", "747897928@qq.com"))
                .description("一款基于Java语言开发的个人博客系统")
                .termsOfServiceUrl("")
                .version("v0.0.1")
                .build();
    }

}
