package com.tierguardians.finances.controller;

import com.tierguardians.finances.config.CsrfTokenStorage;
import com.tierguardians.finances.dto.ApiResponse;
import com.tierguardians.finances.dto.MyPageResponseDto;
import com.tierguardians.finances.dto.UserLoginRequestDto;
import com.tierguardians.finances.dto.UserSignupRequestDto;
import com.tierguardians.finances.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final CsrfTokenStorage csrfTokenStorage;


    public UserController(UserService userService, CsrfTokenStorage csrfTokenStorage) {
        this.userService = userService;
        this.csrfTokenStorage = csrfTokenStorage;
    }

    @GetMapping("/csrf")
    public ResponseEntity<?> getCsrfToken() {
        String token = UUID.randomUUID().toString();
        return ResponseEntity.ok(ApiResponse.success(Map.of("csrfToken", token)));
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody UserSignupRequestDto dto) {
        userService.signup(dto);
        ApiResponse<Void> response = new ApiResponse<>(true, 201, "회원가입 성공", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto dto, HttpServletResponse response) {
        // 1. 인증 및 토큰 생성
        Map<String, String> tokens = userService.login(dto.getUserId(), dto.getPassword());

        // 2. access / refresh 토큰 쿠키로 전송
        addCookie(response, "accessToken", tokens.get("accessToken"));
        addCookie(response, "refreshToken", tokens.get("refreshToken"));

        // 3. CSRF 토큰 생성 후 응답 바디에 포함 (클라이언트가 sessionStorage에 저장)
        String csrfToken = UUID.randomUUID().toString();
        csrfTokenStorage.store(dto.getUserId(), csrfToken);

        return ResponseEntity.ok(
                ApiResponse.success(Map.of("csrfToken", csrfToken))
        );
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        // 세션 무효화 (현재는 세션 사용 안 함 → 의미 없음)
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // ❌ 현재 Stateless 구조에선 무의미
        }

        // 쿠키 삭제
        expireCookie(response, "accessToken");
        expireCookie(response, "refreshToken");

        return ResponseEntity.ok(ApiResponse.success("로그아웃 성공"));
    }

    // 내 정보 조회
    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<MyPageResponseDto>> getMyPage(Authentication authentication) {
        String userId = authentication.getName(); // JWT에서 추출된 userId
        MyPageResponseDto response = userService.getMyPage(userId);
        return ResponseEntity.ok(ApiResponse.<MyPageResponseDto>builder()
                .success(true)
                .code(200)
                .message("내 정보 조회 성공")
                .data(response)
                .build());
    }


    // 로그인 시 사용하는 메서드
    private void addCookie(HttpServletResponse response, String name, String value) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .domain("frontend.local")
                .path("/") // 경로 반드시 동일
                .maxAge(60 * 60 * 1) // 1시간
                .httpOnly(true)
                .secure(false) // 개발용: false (HTTPS면 true)
                .sameSite("Lax") // 이 값도 같아야 함
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    // 로그아웃 시 삭제하는 메서드
    private void expireCookie(HttpServletResponse response, String name) {
        ResponseCookie cookie = ResponseCookie.from(name, "")
                .domain("frontend.local")
                .path("/") // 동일하게 맞춰야 함
                .maxAge(0) // 만료
                .httpOnly(true) // login 때와 같게
                .secure(false) // login 때와 같게
                .sameSite("Lax") // login 때와 같게
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

}