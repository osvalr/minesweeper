package com.osvalr.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan
@EntityScan
@EnableSwagger2
@Configuration
public class Application {

    private static final ApiInfo API_INFO = new ApiInfo(
            "Minesweeper API",
            "Minesweeper API challenge",
            "",
            "Terms of service",
            new Contact("Osval Reyes", "@osvalr", "osvapps@gmail.com"),
            "Copyleft ;)", ".", Collections.emptyList());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket provideDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.osvalr.minesweeper.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(API_INFO);
    }
}

