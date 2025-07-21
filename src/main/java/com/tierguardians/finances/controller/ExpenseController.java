package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Expense;
import com.tierguardians.finances.dto.ExpenseRequestDto;
import com.tierguardians.finances.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // 소비 내역 조회
    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestParam String userId,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String category
    ) {
        List<Expense> expenses = expenseService.getExpenses(userId, month, category);
        return ResponseEntity.ok(expenses);
    }

    // 소비 등록
    @PostMapping
    public ResponseEntity<Map<String, String>> addExpense(@RequestBody ExpenseRequestDto dto) {
        expenseService.addExpense(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "소비 내역 등록 완료"));
    }
}