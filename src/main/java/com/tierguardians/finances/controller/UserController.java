package com.tierguardians.finances.controller;

import com.tierguardians.finances.dto.ApiResponse;
import com.tierguardians.finances.dto.MyPageResponseDto;
import com.tierguardians.finances.dto.UserLoginRequestDto;
import com.tierguardians.finances.dto.UserSignupRequestDto;
import com.tierguardians.finances.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService service) {
        this.userService = service;
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
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody UserLoginRequestDto dto) {
        boolean result = userService.login(dto.getUserId(), dto.getPassword());

        if (result) {
            ApiResponse<Void> response = new ApiResponse<>(true, 200, "로그인 성공", null);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Void> response = new ApiResponse<>(false, 401, "로그인 실패: 아이디 또는 비밀번호 오류", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


    // 내 정보 조회
    @GetMapping("/mypage/{userId}")
    public ResponseEntity<ApiResponse<MyPageResponseDto>> getMyPage(@PathVariable String userId) {
        MyPageResponseDto response = userService.getMyPage(userId);
        return ResponseEntity.ok(ApiResponse.<MyPageResponseDto>builder()
                .success(true)
                .code(200)
                .message("내 정보 조회 성공")
                .data(response)
                .build());
    }

}