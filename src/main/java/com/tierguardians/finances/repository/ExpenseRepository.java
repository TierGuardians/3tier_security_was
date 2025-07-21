package com.tierguardians.finances.repository;

import com.tierguardians.finances.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE e.userId = :userId AND FUNCTION('DATE_FORMAT', e.spentAt, '%Y-%m') = :month")
    List<Expense> findByUserIdAndMonth(@Param("userId") String userId, @Param("month") String month);

    List<Expense> findByUserIdAndCategory(String userId, String category);

    @Query("SELECT e FROM Expense e WHERE e.userId = :userId AND FUNCTION('DATE_FORMAT', e.spentAt, '%Y-%m') = :month AND e.category = :category")
    List<Expense> findByUserIdAndMonthAndCategory(@Param("userId") String userId, @Param("month") String month, @Param("category") String category);

    List<Expense> findByUserId(String userId);
}