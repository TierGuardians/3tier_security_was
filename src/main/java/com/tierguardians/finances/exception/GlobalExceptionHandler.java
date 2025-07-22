package com.tierguardians.finances.exception;

import com.tierguardians.finances.dto.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // IllegalArgumentException 등
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, 400, ex.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }

    // NullPointerException, IllegalStateException 등
    @ExceptionHandler({ NullPointerException.class, IllegalStateException.class })
    public ResponseEntity<ApiResponse<Void>> handleInternal(RuntimeException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, 500, "서버 내부 오류: " + ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // 나머지 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAll(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, 500, "알 수 없는 오류가 발생했습니다", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // 회원가입 관련 예외
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "중복된 사용자 ID 또는 이메일입니다.";
        if (ex.getMessage().contains("users.email")) {
            message = "이미 사용 중인 이메일입니다.";
        } else if (ex.getMessage().contains("PRIMARY")) {
            message = "이미 사용 중인 사용자 ID입니다.";
        }

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                400,
                message,
                null
        );

        return ResponseEntity.badRequest().body(response);
    }
}
