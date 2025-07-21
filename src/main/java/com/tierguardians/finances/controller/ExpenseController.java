package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Expense;
import com.tierguardians.finances.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestParam String userId,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String category
    ) {
        List<Expense> expenses = expenseService.getExpenses(userId, month, category);
        return ResponseEntity.ok(expenses);
    }
}