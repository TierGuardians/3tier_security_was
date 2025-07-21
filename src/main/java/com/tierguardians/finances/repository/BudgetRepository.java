package com.tierguardians.finances.repository;

import com.tierguardians.finances.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserId(String userId);
    Optional<Budget> findByUserIdAndMonth(String userId, String month);
    List<Budget> findAllByUserId(String userId);
}