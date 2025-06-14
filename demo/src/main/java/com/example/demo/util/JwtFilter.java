package com.example.demo.util;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        String method = request.getMethod();

        return path.equals("/api/v1/users/login")
                || path.equals("/api/v1/admins/login")
                || (path.equals("/api/v1/admins") && method.equalsIgnoreCase("POST"))
                || (path.equals("/api/v1/users") && method.equalsIgnoreCase("POST"));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // You can add JWT validation here in the future
        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);

        // Continue the chain
        filterChain.doFilter(request, response);
    }
}
