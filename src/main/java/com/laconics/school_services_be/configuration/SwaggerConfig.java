package com.laconics.school_services_be.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(
                new Info()
                        .title("School Service Api")
                        .version("1.0.0")
                        .description("Task for Laconics challenge interview"));
    }
}
