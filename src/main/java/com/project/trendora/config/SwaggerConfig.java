package com.project.trendora.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
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
                );
    }
}