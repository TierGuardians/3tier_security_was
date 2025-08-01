package com.tierguardians.finances.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRequestDto {
    @NotBlank(message = "카테고리는 필수입니다.")
    @Size(max = 50, message = "카테고리는 최대 50자까지 가능합니다.")
    private String category;

    @Size(max = 100, message = "설명은 최대 100자까지 가능합니다.")
    private String description;

    @Min(value = 1, message = "금액은 1원 이상이어야 합니다.")
    private int amount;

    @NotNull(message = "지출일자는 필수입니다.")
    private LocalDate spentAt;
}

