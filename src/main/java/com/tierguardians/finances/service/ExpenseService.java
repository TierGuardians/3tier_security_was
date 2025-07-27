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
    public void addExpense(ExpenseRequestDto dto, String userId) {
        Expense expense = new Expense();
        expense.setUserId(userId);
        expense.setCategory(dto.getCategory());
        expense.setDescription(dto.getDescription());
        expense.setAmount(BigDecimal.valueOf(dto.getAmount()));
        expense.setSpentAt(dto.getSpentAt());

        expenseRepository.save(expense);
    }

    // 소비 수정
    @Transactional
    public void updateExpense(Long id, ExpenseUpdateRequestDto dto, String userId) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 소비 내역이 존재하지 않습니다."));

        if (!expense.getUserId().equals(userId)) {
            throw new IllegalArgumentException("해당 소비 내역 수정 권한이 없습니다.");
        }

        expense.setCategory(dto.getCategory());
        expense.setDescription(dto.getDescription());
        expense.setAmount(BigDecimal.valueOf(dto.getAmount()));
        expense.setSpentAt(dto.getSpentAt());
    }

    // 소비 삭제
    public void deleteExpense(Long id, String userId) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 소비 내역이 존재하지 않습니다."));

        if (!expense.getUserId().equals(userId)) {
            throw new IllegalArgumentException("해당 소비 내역 삭제 권한이 없습니다.");
        }

        expenseRepository.delete(expense);
    }
}
