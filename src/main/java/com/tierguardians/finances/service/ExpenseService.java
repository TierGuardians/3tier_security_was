package com.tierguardians.finances.service;

import com.tierguardians.finances.domain.Expense;
import com.tierguardians.finances.dto.ExpenseRequestDto;
import com.tierguardians.finances.dto.ExpenseUpdateRequestDto;
import com.tierguardians.finances.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 소비 수정
    @Transactional
    public void updateExpense(Long id, ExpenseUpdateRequestDto dto) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 소비 내역이 존재하지 않습니다."));

        expense.setCategory(dto.getCategory());
        expense.setDescription(dto.getDescription());
        expense.setAmount(BigDecimal.valueOf(dto.getAmount()));
        expense.setSpentAt(dto.getSpentAt());

        // JPA의 dirty checking에 의해 자동으로 update 됨
    }

}
