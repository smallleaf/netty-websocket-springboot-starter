package com.share1024.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 18:58
 * \* Description:
 * \
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("websocket测试平台")
                        .version("1.0")
                        .title("websocket测试平台")
                        .contact(new Contact("yesheng","",""))
                        .build()).select()
                        .apis(RequestHandlerSelectors.basePackage("com.share1024.test.controller"))
                        .paths(PathSelectors.any())
                        .build();
        return docket;
    }
}