//package com.ua07.apigateway.filters;
//
//import com.ua07.shared.jwt.JwtService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.Enumeration;
//import java.util.List;
//
//@Component
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//    @Autowired
//   // private JwtService jwtService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            try {
//                String userId = jwtService.extractUserId(token);
//                if (userId != null && jwtService.isTokenValid(token)) {
//                    // Wrap the request to add X-User-Id
//                    HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(request) {
//                        @Override
//                        public String getHeader(String name) {
//                            if ("X-User-Id".equalsIgnoreCase(name)) {
//                                return userId;
//                            }
//                            return super.getHeader(name);
//                        }
//
//                        @Override
//                        public Enumeration<String> getHeaderNames() {
//                            List<String> names = Collections.list(super.getHeaderNames());
//                            if (!names.contains("X-User-Id")) {
//                                names.add("X-User-Id");
//                            }
//                            return Collections.enumeration(names);
//                        }
//
//                        @Override
//                        public Enumeration<String> getHeaders(String name) {
//                            if ("X-User-Id".equalsIgnoreCase(name)) {
//                                return Collections.enumeration(List.of(userId));
//                            }
//                            return super.getHeaders(name);
//                        }
//                    };
//
//                    filterChain.doFilter(wrappedRequest, response);
//                    return;
//                }
//            } catch (Exception e) {
//                // Log or handle invalid JWT
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//        }
//
//        // Proceed without user ID if no/invalid token
//        filterChain.doFilter(request, response);
//    }
//}
