package com.tierguardians.finances.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class BudgetDto {
    private Long id;
    private String month;
    private BigDecimal amount;
}
