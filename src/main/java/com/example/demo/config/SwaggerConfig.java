package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BV-resource Demo")
                        .version("1.0")
                        .description("This API allows students to upload, access, " +
                                "and manage academic resources such as PDF notes, previous year papers, " +
                                "and study material. Developed using Spring Boot and deployed on Render," +
                                " it provides full Swagger documentation for testing and integration."));
    }
}
