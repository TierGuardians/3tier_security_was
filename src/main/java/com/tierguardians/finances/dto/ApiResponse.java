package com.tierguardians.finances.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;

    // 성공 응답 헬퍼
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, 200, "요청이 성공했습니다.", data);
    }

    public static <T> ApiResponse<T> success(T data, int status) {
        return new ApiResponse<>(true, status, "요청이 성공했습니다.", data);
    }
}
