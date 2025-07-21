package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Budget;
import com.tierguardians.finances.service.BudgetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getBudgets(
            @RequestParam String userId,
            @RequestParam(required = false) String month
    ) {
        if (month != null) {
            Budget budget = budgetService.getBudget(userId, month);
            return ResponseEntity.ok(budget);
        } else {
            List<Budget> budgets = budgetService.getAllBudgets(userId);
            return ResponseEntity.ok(budgets);
        }
    }

}
