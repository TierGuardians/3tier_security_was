package com.tierguardians.finances.service;

import com.tierguardians.finances.domain.Budget;
import com.tierguardians.finances.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    // 예산 조회
    public Budget getBudget(String userId, String month) {
        return budgetRepository.findByUserIdAndMonth(userId, month)
                .orElseThrow(() -> new IllegalArgumentException("해당 예산이 존재하지 않습니다."));
    }
    public List<Budget> getAllBudgets(String userId) {
        return budgetRepository.findAllByUserId(userId);
    }

}
