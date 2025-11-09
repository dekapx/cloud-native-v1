package com.dekapx.weather.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI creditScoreApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Weather API v1.0")
                        .description("Weather API v1.0"));
    }
}
