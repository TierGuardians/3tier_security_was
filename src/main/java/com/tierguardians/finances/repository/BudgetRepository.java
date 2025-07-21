package com.tierguardians.finances.repository;

import com.tierguardians.finances.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserId(String userId);
}