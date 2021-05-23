package com.maple.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *swagger2 API文档配置
 *@author zxzh
 *@date 2021/5/16
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket creatRestfulApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//基本信息
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.maple.web.controller"))//为controller层生成文档
                .paths(PathSelectors.any())//网络路径
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Swagger UI 演示")
                .description("maple web tiny")
                .version("1.0")
                .build();
    }
}
