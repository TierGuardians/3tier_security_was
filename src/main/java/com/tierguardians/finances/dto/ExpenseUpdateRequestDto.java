package com.tierguardians.finances.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseUpdateRequestDto {
    private String category;
    private String description;
    private int amount;
    private LocalDate spentAt;
}