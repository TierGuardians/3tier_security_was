package com.tierguardians.finances.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UserInfoDto {
    private String userId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}