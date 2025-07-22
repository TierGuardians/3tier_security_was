package com.tierguardians.finances.exception;

import com.tierguardians.finances.dto.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 공통 응답 생성 메서드
    private <T> ResponseEntity<ApiResponse<T>> buildResponse(HttpStatus status, String message, T data) {
        return ResponseEntity.status(status).body(ApiResponse.<T>builder()
                .success(false)
                .code(status.value())
                .message(message)
                .data(data)
                .build());
    }

    /**
     * 잘못된 요청: 유효성, 비즈니스 예외
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    /**
     * 중복된 키 제약 위반 (회원가입 등)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "데이터 무결성 오류입니다.";

        if (ex.getMessage().contains("users.email")) {
            message = "이미 사용 중인 이메일입니다.";
        } else if (ex.getMessage().contains("PRIMARY") || ex.getMessage().contains("users.user_id")) {
            message = "이미 사용 중인 사용자 ID입니다.";
        }

        return buildResponse(HttpStatus.BAD_REQUEST, message, null);
    }

    /**
     * 서버 내부 오류: NullPointer, IllegalState 등
     */
    @ExceptionHandler({NullPointerException.class, IllegalStateException.class})
    public ResponseEntity<ApiResponse<Void>> handleInternalErrors(RuntimeException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류: " + ex.getMessage(), null);
    }

    /**
     * 그 외 모든 예외
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 오류: " + ex.getMessage(), null);
    }
}
