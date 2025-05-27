package cz.cvut.fel.nss.gardeningrental.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Userservice routes
                .route("userservice-auth", r -> r
                        .path("/auth/**")
                        .uri("http://userservice:8082"))
                .route("userservice-users", r -> r
                        .path("/users/**")
                        .uri("http://userservice:8082"))
                        
                // Productservice routes
                .route("productservice-products", r -> r
                        .path("/products/**")
                        .uri("http://productservice:8081"))
                .route("productservice-categories", r -> r
                        .path("/categories/**")
                        .uri("http://productservice:8081"))
                .route("productservice-reviews", r -> r
                        .path("/reviews/**")
                        .uri("http://productservice:8081"))
                        
                // Branchservice routes
                .route("branchservice-branches", r -> r
                        .path("/branch/**")
                        .uri("http://branchservice:8084"))
                .route("branchservice-items", r -> r
                        .path("/items/**")
                        .uri("http://branchservice:8084"))
                        
                // Loggingservice routes
                // this service does not use https requests so probably can be removed from here
                .route("loggingservice", r -> r
                        .path("/logs/**")
                        .uri("http://loggingservice:8083"))
                .build();
    }
}
