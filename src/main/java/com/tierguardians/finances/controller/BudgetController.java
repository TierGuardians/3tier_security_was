package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Budget;
import com.tierguardians.finances.dto.ApiResponse;
import com.tierguardians.finances.dto.BudgetRequestDto;
import com.tierguardians.finances.dto.BudgetUpdateRequestDto;
import com.tierguardians.finances.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
            @RequestParam String userId,
            @RequestParam(required = false) String month
    ) {
        if (month != null) {
            Budget budget = budgetService.getBudget(userId, month);
            return ResponseEntity.ok(ApiResponse.success(budget));
        } else {
            List<Budget> budgets = budgetService.getAllBudgets(userId);
            return ResponseEntity.ok(ApiResponse.success(budgets));
        }
    }

    // 예산 등록
    @PostMapping
    public ResponseEntity<ApiResponse<String>> addBudget(@RequestBody BudgetRequestDto dto) {
        budgetService.addBudget(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("예산 등록 완료", 201));
    }

    // 예산 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateBudget(@PathVariable Long id,
                                                            @RequestBody BudgetUpdateRequestDto dto) {
        budgetService.updateBudget(id, dto);
        return ResponseEntity.ok(ApiResponse.success("예산 수정 완료"));
    }

    // 예산 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.ok(ApiResponse.success("예산 삭제 완료"));
    }

}
