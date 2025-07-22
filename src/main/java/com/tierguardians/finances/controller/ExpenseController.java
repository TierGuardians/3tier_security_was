package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Expense;
import com.tierguardians.finances.dto.ApiResponse;
import com.tierguardians.finances.dto.ExpenseRequestDto;
import com.tierguardians.finances.dto.ExpenseUpdateRequestDto;
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
    public ResponseEntity<ApiResponse<List<Expense>>> getExpenses(
            @RequestParam String userId,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String category
    ) {
        List<Expense> expenses = expenseService.getExpenses(userId, month, category);
        return ResponseEntity.ok(ApiResponse.success(expenses));
    }

    // 소비 등록
    @PostMapping
    public ResponseEntity<ApiResponse<String>> addExpense(@RequestBody ExpenseRequestDto dto) {
        expenseService.addExpense(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("소비 내역 등록 완료", 201));
    }

    // 소비 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseUpdateRequestDto dto) {
        expenseService.updateExpense(id, dto);
        return ResponseEntity.ok(ApiResponse.success("소비 내역 수정 완료"));
    }

    // 소비 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok(ApiResponse.success("소비 내역 삭제 완료"));
    }
}