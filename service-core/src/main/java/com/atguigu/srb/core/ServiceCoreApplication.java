package com.atguigu.srb.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 吴浩远
 * @date 2022/5/11 21:43
 * @Description
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.atguigu.srb"}) // 扩大组件扫描范围
public class ServiceCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCoreApplication.class, args);
    }
}

