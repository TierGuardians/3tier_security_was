package com.tierguardians.finances.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AssetUpdateRequestDto {
    private String name;
    private String type;
    private BigDecimal amount;
}