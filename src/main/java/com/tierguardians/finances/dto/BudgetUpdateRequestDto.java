package com.tierguardians.finances.dto;

import lombok.Getter;

@Getter
public class BudgetUpdateRequestDto {
    private String month;
    private int amount;
}