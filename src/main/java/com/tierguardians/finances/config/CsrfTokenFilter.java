package com.tierguardians.finances.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfTokenFilter extends OncePerRequestFilter {
    private final CsrfTokenStorage csrfTokenStorage;

    public CsrfTokenFilter(CsrfTokenStorage csrfTokenStorage) {
        this.csrfTokenStorage = csrfTokenStorage;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            chain.doFilter(request, response);
            return;
        }

        String path = request.getRequestURI();
        if (path.startsWith("/users/login") || path.startsWith("/users/signup") || path.startsWith("/users/csrf") || path.startsWith("/users/logout")) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("X-CSRF-TOKEN");
        if (token == null || token.isBlank()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF 토큰 누락");
            return;
        }

        // ✅ 추가적인 토큰 값 검증이 필요하면 여기에 구현 가능
        // 🔐 인증된 사용자 ID 확인
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않은 사용자");
            return;
        }

        String userId = auth.getName();

        // ✅ 저장된 토큰과 비교
        if (!csrfTokenStorage.isValid(userId, token)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "잘못된 CSRF 토큰");
            return;
        }

        chain.doFilter(request, response);
    }
}