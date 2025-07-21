package com.tierguardians.finances.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MyPageResponseDto {
    private UserInfoDto user;
    private List<BudgetDto> budgets;
    private List<AssetDto> assets;
    private List<ExpenseDto> expenses;
}