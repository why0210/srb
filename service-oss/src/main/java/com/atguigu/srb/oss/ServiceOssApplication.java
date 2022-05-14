package com.atguigu.srb.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 吴浩远
 * @date 2022/5/13 9:03
 * @Description
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.atguigu.srb", "com.atguigu.srb.common"})
public class ServiceOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApplication.class, args);
    }
}
