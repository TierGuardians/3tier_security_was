package com.tierguardians.finances.service;

import com.tierguardians.finances.domain.Budget;
import com.tierguardians.finances.dto.BudgetRequestDto;
import com.tierguardians.finances.dto.BudgetUpdateRequestDto;
import com.tierguardians.finances.repository.BudgetRepository;
import com.tierguardians.finances.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    // 예산 조회
    public Budget getBudget(String userId, String month) {
        return budgetRepository.findByUserIdAndMonth(userId, month)
                .orElseThrow(() -> new IllegalArgumentException("해당 예산이 존재하지 않습니다."));
    }

    public List<Budget> getAllBudgets(String userId) {
        return budgetRepository.findAllByUserId(userId);
    }

    // 이번달 예산 조회
    public BigDecimal getCurrentMonthBudget(String userId) {
        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        return budgetRepository.findByUserIdAndMonth(userId, currentMonth)
                .map(Budget::getAmount)
                .orElse(BigDecimal.ZERO);
    }

    // 예산 등록
    public void addBudget(BudgetRequestDto dto, String userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        Budget budget = new Budget();
        budget.setUserId(userId);
        budget.setMonth(dto.getMonth());
        budget.setAmount(BigDecimal.valueOf(dto.getAmount()));
        budget.setCreatedAt(LocalDateTime.now());

        budgetRepository.save(budget);
    }

    // 예산 수정
    public void updateBudget(Long id, BudgetUpdateRequestDto dto, String userId) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예산 항목이 존재하지 않습니다."));

        if (!budget.getUserId().equals(userId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        budget.setMonth(dto.getMonth());
        budget.setAmount(BigDecimal.valueOf(dto.getAmount()));
        budgetRepository.save(budget);
    }

    // 예산 삭제
    public void deleteBudget(Long id, String userId) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예산 항목이 존재하지 않습니다."));

        if (!budget.getUserId().equals(userId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        budgetRepository.delete(budget);
    }
}
