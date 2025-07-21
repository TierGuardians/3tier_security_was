package com.tierguardians.finances.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ExpenseDto {
    private Long id;
    private String category;
    private String description;
    private BigDecimal amount;
    private LocalDate spentAt;
}
