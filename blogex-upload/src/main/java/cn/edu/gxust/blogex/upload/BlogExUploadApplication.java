package cn.edu.gxust.blogex.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>description:  </p>
 * <p>create: 2021/6/16 11:47</p>
 *
 * @author zhaoyijie
 * @version v1.0
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"cn.edu.gxust.blogex.upload"})
public class BlogExUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogExUploadApplication.class, args);
    }
}