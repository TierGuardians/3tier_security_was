package com.tierguardians.finances.controller;

import com.tierguardians.finances.dto.ApiResponse;
import com.tierguardians.finances.dto.MyPageResponseDto;
import com.tierguardians.finances.dto.UserLoginRequestDto;
import com.tierguardians.finances.dto.UserSignupRequestDto;
import com.tierguardians.finances.repository.UserRepository;
import com.tierguardians.finances.service.UserService;
import com.tierguardians.finances.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UserController(UserService service, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = service;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody UserLoginRequestDto dto) {
        try {
            Map<String, String> tokens = userService.login(dto.getUserId(), dto.getPassword());

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    true, 200, "로그인 성공", tokens
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    false, 401, "로그인 실패: " + e.getMessage(), null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
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


}