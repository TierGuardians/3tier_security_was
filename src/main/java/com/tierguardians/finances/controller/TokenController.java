package com.tierguardians.finances.controller;

import com.tierguardians.finances.dto.ApiResponse;
import com.tierguardians.finances.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<Map<String, String>>> reissue(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        Map<String, String> tokens = tokenService.reissueTokens(refreshToken);
        return ResponseEntity.ok(ApiResponse.success("토큰 재발급 성공", tokens));
    }
}
