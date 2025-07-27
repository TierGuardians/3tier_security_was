package com.tierguardians.finances.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AssetRequestDto {
    private String name;
    private String type;
    private BigDecimal amount;
}
