package com.tierguardians.finances.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupRequestDto {
    private String userId;
    private String password;
    private String name;
}
