package com.tierguardians.finances.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;
    private final long ACCESS_EXPIRATION = 1000L * 60 * 15; // 15분
    private final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7일

    public String generateAccessToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

