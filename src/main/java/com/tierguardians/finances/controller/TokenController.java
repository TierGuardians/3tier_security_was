package com.tierguardians.finances.controller;

import com.tierguardians.finances.dto.ApiResponse;
import com.tierguardians.finances.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<Map<String, String>>> reissueToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail(400, "리프레시 토큰이 필요합니다."));
        }

        String refreshToken = authHeader.substring(7);
        try {
            Map<String, String> newTokens = tokenService.reissueTokens(refreshToken);
            return ResponseEntity.ok(ApiResponse.success("토큰 재발급 성공", newTokens));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.fail(401, e.getMessage()));
        }
    }
}
