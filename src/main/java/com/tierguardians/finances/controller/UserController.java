package com.tierguardians.finances.controller;

import com.tierguardians.finances.dto.UserLoginRequestDto;
import com.tierguardians.finances.dto.UserSignupRequestDto;
import com.tierguardians.finances.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequestDto dto) {
        userService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "회원가입 완료"));
    }

    // 로그인
    @PostMapping("login")
    public Map<String, Object> login(@RequestBody UserLoginRequestDto request) {
        boolean result = userService.login(request.getUserId(), request.getPassword());
        Map<String, Object> response = new HashMap<>();
        response.put("success", result);
        return response;
    }
}