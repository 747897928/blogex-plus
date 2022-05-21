package cn.edu.gxust.blogex.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 * @author zhaoyijie
 * @since 2021/6/5 11:11
 */
@ComponentScan(basePackages = {"cn.edu.gxust.blogex"})
@SpringBootApplication
@EnableDiscoveryClient
public class BlogExGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogExGateWayApplication.class, args);
    }
}
