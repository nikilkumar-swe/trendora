package com.project.trendora.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "Bearer Authentication";
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Trendora API")
                                .description("""
                                        Trendora is a scalable and secure e-commerce backend application built using Spring Boot.
                                        It provides RESTful APIs for product management, user authentication, shopping cart operations,
                                        order processing, and payment handling.

                                        Features:
                                        • JWT Authentication and Authorization
                                        • Product and Inventory Management
                                        • Shopping Cart Management
                                        • Order Processing
                                        • Input Validation
                                        • Global Exception Handling
                                        • Pagination and Sorting
                                        • API Documentation using Swagger/OpenAPI
                                        """)
                                .version("v1.0.0")
                                .contact(
                                        new Contact()
                                                .name("Nikhil Kumar Rana")
                                                .email("kumarnikil220@gmail.com")
                                )
                )
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .description("Enter your JWT access token")
                                )
                );
    }
}