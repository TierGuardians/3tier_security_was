package com.tierguardians.finances.controller;

import com.tierguardians.finances.config.CsrfTokenStorage;
import com.tierguardians.finances.dto.*;
import com.tierguardians.finances.service.UserService;
import com.tierguardians.finances.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService, CsrfTokenStorage csrfTokenStorage) {
        this.userService = userService;
    }

    @GetMapping("/csrf")
    public ResponseEntity<?> getCsrfToken() {
        String token = UUID.randomUUID().toString();
        return ResponseEntity.ok(ApiResponse.success(Map.of("csrfToken", token)));
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody UserSignupRequestDto dto) {
        userService.signup(dto);
        return ResponseEntity.status(201).body(ApiResponse.success("회원가입 성공"));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody UserLoginRequestDto dto, HttpServletResponse response) {
        UserLoginResponseDto loginResponse = userService.login(dto);

        CookieUtil.addCookie(response, "accessToken", loginResponse.getAccessToken());
        CookieUtil.addCookie(response, "refreshToken", loginResponse.getRefreshToken());

        return ResponseEntity.ok(ApiResponse.success(
                Map.of("csrfToken", loginResponse.getCsrfToken())
        ));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 쿠키 삭제
        CookieUtil.expireCookie(response, "accessToken");
        CookieUtil.expireCookie(response, "refreshToken");

        return ResponseEntity.ok(ApiResponse.success("로그아웃 성공"));
    }

    // 내 정보 조회
    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<MyPageResponseDto>> getMyPage(Authentication authentication) {
        String userId = authentication.getName();
        MyPageResponseDto response = userService.getMyPage(userId);
        return ResponseEntity.ok(ApiResponse.success("내 정보 조회 성공", response));
    }
}