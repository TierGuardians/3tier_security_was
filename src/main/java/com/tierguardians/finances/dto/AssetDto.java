package com.tierguardians.finances.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AssetDto {
    private Long id;
    private String name;
    private String type;
    private BigDecimal amount;
}
