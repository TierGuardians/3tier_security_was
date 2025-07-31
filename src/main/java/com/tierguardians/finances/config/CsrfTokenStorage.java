package com.tierguardians.finances.config;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CsrfTokenStorage {
    private final Map<String, String> tokenMap = new ConcurrentHashMap<>();

    public void store(String userId, String token) {
        tokenMap.put(userId, token);
    }

    public boolean isValid(String userId, String token) {
        return token != null && token.equals(tokenMap.get(userId));
    }
}