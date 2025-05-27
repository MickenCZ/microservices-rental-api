package cz.cvut.fel.nss.gardeningrental.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    // Pro konzistenci s ostatními službami použijeme stejný klíč
    private static final String SECRET_KEY = "my-very-secure-and-long-secret-key-123!";
    
    // Endpointy, které nevyžadují autentizaci
    private static final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
            "/auth/login",
            "/auth/register",
            "/products/exists",
            "/swagger-ui",
            "/swagger-resources",
            "/v3/api-docs",
            "/h2-console",
            "/swagger-ui",
            "/swagger-resources",
            "/v3/api-docs",
            "/h2-console"
            );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        
        // Kontrola, zda jde o veřejný endpoint, který nevyžaduje autentizaci
        if (isPublicEndpoint(path)) {
            return chain.filter(exchange);
        }
        
        // Získání JWT tokenu z hlavičky
        String authHeader = request.getHeaders().getFirst("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        
        String token = authHeader.substring(7);
        
        try {
            // Validace tokenu
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            // Předání informací z tokenu pro možné další použití
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Username", claims.getSubject())
                    .build();
            
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(path::startsWith);
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public int getOrder() {
        return -1; // Vyšší priorita (menší číslo = vyšší priorita)
    }
}