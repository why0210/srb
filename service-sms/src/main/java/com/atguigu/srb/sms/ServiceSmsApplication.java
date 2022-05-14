package com.atguigu.srb.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 吴浩远
 * @date 2022/5/12 20:59
 * @Description
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.atguigu.srb", "com.atguigu.srb.common"}) // 扩大组件扫描范围
public class ServiceSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceSmsApplication.class, args);
    }
}
