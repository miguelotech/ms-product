package com.synopsis.product.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product Service API",
                version = "1.0.0",
                description = "Microservicio para la gestion de productos"
        ),
        servers = {
                @Server(url = "/", description = "Servidor por defecto")
        }
)
public class OpenApiConfig {
}
