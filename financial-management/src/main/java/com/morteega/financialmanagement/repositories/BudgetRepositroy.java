package com.morteega.financialmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morteega.financialmanagement.model.Budget;

public interface BudgetRepositroy extends JpaRepository<Budget, Long>{
    List<Budget> findByUserId(Long userId);
}
