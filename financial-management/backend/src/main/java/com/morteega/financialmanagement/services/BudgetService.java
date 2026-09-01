package com.morteega.financialmanagement.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.morteega.financialmanagement.dtos.BudgetRequestDTO;
import com.morteega.financialmanagement.dtos.BudgetResponseDTO;
import com.morteega.financialmanagement.model.Budget;
import com.morteega.financialmanagement.model.users.User;
import com.morteega.financialmanagement.repositories.BudgetRepositroy;
import com.morteega.financialmanagement.repositories.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BudgetService {

    private BudgetRepositroy budgetRepository;
    private UserRepository userRepository;
    

    public BudgetResponseDTO createBudget(BudgetRequestDTO budgetRequestDTO, Long userId){
        Budget budget= new Budget();
        if(this.budgetRepository.existsById(budget.getId())){
            throw new EntityExistsException("This budget already exists");
        }
        budget.setName(budgetRequestDTO.getName());
        budget.setgoalAmmount(budgetRequestDTO.getGoalAmmount());
        budget.setactualAmmount(budgetRequestDTO.getActualAmmount());
        User user= this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("The user couldn't be found"));
        budget.setUser(user);
        this.budgetRepository.save(budget);
        BudgetResponseDTO budgetResponseDTO=this.toDto(budget);
        return budgetResponseDTO;
    }

    private BudgetResponseDTO toDto(Budget budget){
        BudgetResponseDTO budgetResponseDTO= new BudgetResponseDTO();
        budgetResponseDTO.setId(budget.getId());
        budgetResponseDTO.setActualAmmount(budget.getactualAmmount());
        budgetResponseDTO.setGoalAmmount(budget.getgoalAmmount());
        budgetResponseDTO.setName(budget.getName());
        return budgetResponseDTO;
    }
}
