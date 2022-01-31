package com.matheusjmoura.postapi.commons.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.matheusjmoura.postapi"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(false);
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder
            .builder()
            .operationsSorter(OperationsSorter.METHOD)
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Post REST API")
            .description("Simple REST API to create user, post and comment")
            .version("1.0.0")
            .license("Apache License Version 2.0")
            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
            .contact(new Contact("Matheus José de Moura", "https://www.linkedin.com/in/matheusjmoura/",
                "matheusjosemoura@hotmail.com"))
            .build();
    }
}
