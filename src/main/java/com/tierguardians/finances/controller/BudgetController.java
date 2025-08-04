package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Budget;
import com.tierguardians.finances.dto.ApiResponse;
import com.tierguardians.finances.dto.BudgetRequestDto;
import com.tierguardians.finances.dto.BudgetUpdateRequestDto;
import com.tierguardians.finances.service.BudgetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    // 예산 조회
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getBudgets(
            @RequestParam(required = false) String month,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        if (month != null) {
            Budget budget = budgetService.getBudget(userId, month);
            return ResponseEntity.ok(ApiResponse.success("예산 단일 조회 성공", budget));
        }

        List<Budget> budgets = budgetService.getAllBudgets(userId);
        return ResponseEntity.ok(ApiResponse.success("예산 전체 조회 성공", budgets));
    }

    // 이번달 예산 조회
    @GetMapping("/monthly-total")
    public ResponseEntity<ApiResponse<BigDecimal>> getMonthlyBudget(Authentication authentication) {
        String userId = authentication.getName();
        BigDecimal budget = budgetService.getCurrentMonthBudget(userId);
        return ResponseEntity.ok(ApiResponse.success("이번 달 예산 조회 성공", budget));
    }

    // 예산 등록
    @PostMapping
    public ResponseEntity<ApiResponse<String>> addBudget(@RequestBody BudgetRequestDto dto,
                                                         Authentication authentication) {
        String userId = authentication.getName();
        budgetService.addBudget(dto, userId);
        return ResponseEntity.status(201).body(ApiResponse.success("예산 등록 완료", 201));
    }

    // 예산 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateBudget(@PathVariable Long id,
                                                            @RequestBody BudgetUpdateRequestDto dto,
                                                            Authentication authentication) {
        String userId = authentication.getName();
        budgetService.updateBudget(id, dto, userId);
        return ResponseEntity.ok(ApiResponse.success("예산 수정 완료"));
    }

    // 예산 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBudget(@PathVariable Long id,
                                                            Authentication authentication) {
        String userId = authentication.getName();
        budgetService.deleteBudget(id, userId);
        return ResponseEntity.ok(ApiResponse.success("예산 삭제 완료"));
    }
}
