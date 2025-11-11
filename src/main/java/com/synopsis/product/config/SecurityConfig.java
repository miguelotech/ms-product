package com.synopsis.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.GET, "/api/products/**").hasAuthority("SCOPE_products.read")
                        .pathMatchers(HttpMethod.POST, "/api/products/**").hasAuthority("SCOPE_products.write")
                        .pathMatchers(HttpMethod.PUT, "/api/products/**").hasAuthority("SCOPE_products.write")
                        .pathMatchers(HttpMethod.DELETE, "/api/products/**").hasAuthority("SCOPE_products.write")
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
                .build();
    }
}
