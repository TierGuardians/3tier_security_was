package com.tierguardians.finances.repository;

import com.tierguardians.finances.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE e.userId = :userId AND e.spentAt BETWEEN :startDate AND :endDate")
    List<Expense> findByUserIdAndMonth(@Param("userId") String userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Expense> findByUserIdAndCategory(String userId, String category);

    @Query("SELECT e FROM Expense e WHERE e.userId = :userId AND e.category = :category AND e.spentAt BETWEEN :startDate AND :endDate")
    List<Expense> findByUserIdAndMonthAndCategory(@Param("userId") String userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("category") String category);

    List<Expense> findByUserId(String userId);

    List<Expense> findByUserIdAndSpentAtBetween(String userId, LocalDate start, LocalDate end);
}