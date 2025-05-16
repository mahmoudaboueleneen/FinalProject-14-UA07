package com.ua07.apigateway.filters;

import com.ua07.shared.auth.AuthConstants;
import com.ua07.shared.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Component
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthGlobalFilter.class);

    @Autowired
    public JwtAuthGlobalFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        logger.info("Incoming request: {} {}", request.getMethod(), path);

        // Allow auth-related paths and preflight CORS requests without JWT
        if (path.contains("/auth") || request.getMethod() == HttpMethod.OPTIONS) {
            logger.debug("Skipping JWT auth for path: {}", path);
            return chain.filter(exchange);
        }

        // Extract token from cookies
        String token = null;
        if (request.getCookies().containsKey(AuthConstants.ACCESS_TOKEN_COOKIE)) {
            token = request.getCookies().getFirst(AuthConstants.ACCESS_TOKEN_COOKIE).getValue();
            logger.debug("JWT token found in cookie");
        } else {
            logger.warn("No JWT token found in cookies for path: {}", path);
        }

        if (token == null || token.isEmpty()) {
            logger.warn("Missing or empty JWT token, denying access");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            if (jwtService.isTokenValid(token)) {
                UUID userId = jwtService.extractUserId(token);

                if (userId != null) {
                    logger.info("Authenticated user: {}", userId);
                    // Forward user ID to downstream services
                    ServerHttpRequest mutatedRequest = request.mutate()
                            .header(AuthConstants.USER_ID_HEADER, userId.toString())
                            .build();

                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                    return chain.filter(mutatedExchange);
                } else {
                    logger.error("Failed to extract user ID from token");
                }
            } else {
                logger.warn("Invalid JWT token");
            }

            // Invalid token
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();

        } catch (Exception e) {
            logger.error("Error during JWT validation: {}", e.getMessage(), e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

    }

    @Override
    public int getOrder() {
        return -1; // High precedence
    }

}
