package cn.edu.gxust.blogex.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>description:  </p>
 * <p>create: 2021/6/5 10:51</p>
 *
 * @author zhaoyijie
 * @version v1.0
 */
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.edu.gxust.blogex.api.feigin"})
@SpringBootApplication(scanBasePackages = {"cn.edu.gxust.blogex.api"})
public class BlogExApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogExApiApplication.class, args);
    }

}
