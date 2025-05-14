package com.ua07.apigateway.filters;

import com.ua07.shared.auth.AuthConstants;
import com.ua07.shared.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    @Autowired
    public JwtAuthGlobalFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        String token = null;

        if (exchange.getRequest().getCookies().containsKey(AuthConstants.ACCESS_TOKEN_COOKIE)) {
            token = exchange.getRequest().getCookies().getFirst(AuthConstants.ACCESS_TOKEN_COOKIE).getValue();
        }

        if (token != null) {
            try {
                String userId = String.valueOf(jwtService.extractUserId(token));

                if (userId != null && jwtService.isTokenValid(token)) {
                    // Add user ID header for downstream microservices
                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header(AuthConstants.USER_ID_HEADER, userId)
                            .build();

                    ServerWebExchange mutatedExchange =
                            exchange.mutate().request(mutatedRequest).build();

                    return chain.filter(mutatedExchange);
                } else {
                    exchange.getResponse()
                            .setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            } catch (Exception e) {
                exchange.getResponse()
                        .setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        // No token found
        String path = exchange.getRequest().getURI().getPath();
        if (path.contains("/auth")) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1; // High precedence
    }

}
