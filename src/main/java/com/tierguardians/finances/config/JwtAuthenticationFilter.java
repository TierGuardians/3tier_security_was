package com.tierguardians.finances.config;

import com.tierguardians.finances.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // JWT 쿠키에서 추출
        String token = extractJwtFromCookies(request);

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 1. 쿠키에 토큰이 없으면 헤더에서 시도
        if(token == null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            } else {
                log.debug("❗ JWT 토큰 없음: 쿠키에도, 헤더에도 없음");
            }
        }

        // 2. 토큰이 존재하면 검증 및 인증 등록
        if (token != null && jwtUtil.validateToken(token)) {
            if (jwtUtil.validateToken(token)) {
                String userId = jwtUtil.getUsernameFromToken(token);
                List<String> roles = jwtUtil.getRolesFromToken(token);
                if (roles == null) {
                    roles = List.of("ROLE_USER");
                }
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                // SecurityContext가 비어있는 경우에만 등록
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userId, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.debug("✅ 인증 성공: userId={}, roles={}", userId, roles);
                }
            } else {
                log.warn("⛔ 유효하지 않은 JWT 토큰입니다.");
            }
        }  else {
            log.debug("❗ access_token 쿠키가 존재하지 않음");
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                log.debug("🔍 요청에 포함된 쿠키: {}", cookie.getName());
                if ("accessToken".equals(cookie.getName())) {
                    log.debug("✅ accessToken 쿠키 발견");
                    return cookie.getValue();
                }
            }
        } else {
            log.debug("❗ 요청에 쿠키가 하나도 없음");
        }
        return null;
    }
}