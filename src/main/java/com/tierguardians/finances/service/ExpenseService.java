package com.tierguardians.finances.service;

import com.tierguardians.finances.domain.Expense;
import com.tierguardians.finances.dto.ExpenseRequestDto;
import com.tierguardians.finances.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // 소비 내역 조회
    public List<Expense> getExpenses(String userId, String month, String category) {
        if (month != null && category != null) {
            return expenseRepository.findByUserIdAndMonthAndCategory(userId, month, category);
        } else if (month != null) {
            return expenseRepository.findByUserIdAndMonth(userId, month);
        } else if (category != null) {
            return expenseRepository.findByUserIdAndCategory(userId, category);
        } else {
            return expenseRepository.findByUserId(userId);
        }
    }

    // 소비 등록
    public void addExpense(ExpenseRequestDto dto) {
        Expense expense = new Expense();
        expense.setUserId(dto.getUserId());
        expense.setCategory(dto.getCategory());
        expense.setDescription(dto.getDescription());
        expense.setAmount(BigDecimal.valueOf(dto.getAmount()));
        expense.setSpentAt(dto.getSpentAt());

        expenseRepository.save(expense);
    }
}
