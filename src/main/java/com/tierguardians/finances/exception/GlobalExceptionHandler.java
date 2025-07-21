package com.tierguardians.finances.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        ex.printStackTrace(); // 콘솔 로그
        return ResponseEntity.status(500).body(Map.of(
                "status", 500,
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "message", ex.getMessage()
        ));
    }
}
