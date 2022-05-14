package com.atguigu.srb.base.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 吴浩远
 * @date 2022/5/11 20:47
 * @Description swagger配置类，共有两个文档，即两个bean，一个是管理系统的api，另一个是用户前端页面的api。
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    /**
     * 生成接口文档
     *
     * @return 文档实体
     */
    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                //调用描述信息adminApiInfo()
                .apiInfo(adminApiInfo())
                .select()
                //将前缀为admin的接口放在此接口文档中
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();
    }

    /**
     * 生成接口文档
     *
     * @return 文档实体
     */
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                //调用描述信息webApiInfo()
                .apiInfo(webApiInfo())
                .select()
                //将前缀为api的接口放在此接口文档中
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }

    /***********************************描述文档**************************************/

    /**
     * 封装服务后台接口文档信息
     *
     * @return 文档详细信息
     */
    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("尚融宝后台管理系统API文档")
                .description("本文档描述了尚融宝后台管理系统各个模块的接口调用方式")
                .version("1.0")
                .contact(new Contact("wuhaoyuan", null, "2678457483@qq.com"))
                .build();
    }

    /**
     * 封装服务前端接口文档信息
     *
     * @return 文档详细信息
     */
    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("尚融宝前端API文档")
                .description("本文档描述了尚融宝前端系统的接口调用方式")
                .version("1.0")
                .contact(new Contact("wuhaoyuan", null, "2678457483@qq.com"))
                .build();
    }
}
