package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Expense;
import com.tierguardians.finances.dto.ApiResponse;
import com.tierguardians.finances.dto.ExpenseRequestDto;
import com.tierguardians.finances.dto.ExpenseUpdateRequestDto;
import com.tierguardians.finances.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String category,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        List<Expense> expenses = expenseService.getExpenses(userId, month, category);
        return ResponseEntity.ok(ApiResponse.success(expenses));
    }

    // 소비 등록
    @PostMapping
    public ResponseEntity<ApiResponse<String>> addExpense(@RequestBody ExpenseRequestDto dto,
                                                          Authentication authentication) {
        String userId = authentication.getName();
        expenseService.addExpense(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("소비 내역 등록 완료", 201));
    }

    // 소비 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseUpdateRequestDto dto,
            Authentication authentication) {
        String userId = authentication.getName();
        expenseService.updateExpense(id, dto, userId);
        return ResponseEntity.ok(ApiResponse.success("소비 내역 수정 완료"));
    }

    // 소비 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteExpense(@PathVariable Long id,
                                                             Authentication authentication) {
        String userId = authentication.getName();
        expenseService.deleteExpense(id, userId);
        return ResponseEntity.ok(ApiResponse.success("소비 내역 삭제 완료"));
    }
}
