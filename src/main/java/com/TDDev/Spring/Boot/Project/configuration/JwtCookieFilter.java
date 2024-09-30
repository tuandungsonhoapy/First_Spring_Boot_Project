package com.TDDev.Spring.Boot.Project.configuration;

import static com.TDDev.Spring.Boot.Project.configuration.CookieUtils.getCookieValue;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtCookieFilter implements Filter {
    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    public JwtCookieFilter(JwtDecoder jwtDecoder, JwtAuthenticationConverter jwtAuthenticationConverter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // Ép kiểu ServletRequest thành HttpServletRequest để xử lý cookie
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String token = getCookieValue(httpRequest, "identityToken"); // Lấy token từ cookie

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Decode JWT token
                Jwt jwt = jwtDecoder.decode(token);

                // Chuyển JWT thành Authentication
                Authentication authentication = jwtAuthenticationConverter.convert(jwt);

                // Set Authentication vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                log.info("Error: {}", e.getMessage());
            }
        }

        // Tiếp tục với chuỗi filter
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
