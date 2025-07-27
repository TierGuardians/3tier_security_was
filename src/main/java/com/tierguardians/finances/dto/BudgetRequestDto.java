package com.tierguardians.finances.dto;

import lombok.Getter;

@Getter
public class BudgetRequestDto {
    private String month; // 형식: YYYY-MM
    private String category;
    private int amount;
}