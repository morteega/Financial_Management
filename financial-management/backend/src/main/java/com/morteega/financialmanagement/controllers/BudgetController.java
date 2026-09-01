package com.morteega.financialmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morteega.financialmanagement.dtos.BudgetRequestDTO;
import com.morteega.financialmanagement.dtos.BudgetResponseDTO;
import com.morteega.financialmanagement.services.BudgetService;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService){
        this.budgetService=budgetService;
    }

    @PostMapping
    public ResponseEntity<BudgetResponseDTO> createBudget(BudgetRequestDTO budgetRequestDTO, Long userId){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.budgetService.createBudget(budgetRequestDTO, userId));
    }
}
