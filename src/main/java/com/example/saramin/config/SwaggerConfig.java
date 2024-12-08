package com.example.saramin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI openAPI() {

            Info info = new Info()
                    .version("v1.0.0")
                    .title("Swagger-UI")
                    .description("API 테스트");

            String jwtSchemeName = "jwtAuth";
            
            SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
            
            Components components = new Components()
                    .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                            .name(jwtSchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT"));

            return new OpenAPI()
                    .info(info)
                    .addSecurityItem(securityRequirement)
                    .components(components);
        }
}
