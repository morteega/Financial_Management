package com.morteega.financialmanagement.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.morteega.financialmanagement.dtos.FinancialAccountRequestDTO;
import com.morteega.financialmanagement.dtos.FinancialAccountResponseDTO;
import com.morteega.financialmanagement.repositories.FinancialAccountRepository;
import com.morteega.financialmanagement.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import com.morteega.financialmanagement.model.users.User;
import com.morteega.financialmanagement.model.FinancialAccount;

@Service
public class FinancialAccountService {
    private FinancialAccountRepository financialAccountRepository;
    private UserRepository userRepository;

    public FinancialAccountService(FinancialAccountRepository financialAccountRepository, UserRepository userRepository){
        this.financialAccountRepository=financialAccountRepository;
        this.userRepository=userRepository;
    }

    public FinancialAccountResponseDTO createFinancialAccount(User user,FinancialAccountRequestDTO financialAccountRequestDTO){
        FinancialAccount financialAccount = new FinancialAccount();
        financialAccount.setAmount(financialAccountRequestDTO.getAmount());
        financialAccount.setName(financialAccountRequestDTO.getName());
        User checkUser= this.userRepository.findById(financialAccountRequestDTO.getUserId()). orElseThrow(() -> new EntityNotFoundException("User not found"));
        if(user.getId().equals(checkUser.getId()))
            financialAccount.setUser(user);
        if(financialAccountRequestDTO.getUserId().equals(user.getId())) {
            this.financialAccountRepository.save(financialAccount);
            return toDto(financialAccount);
        }else{
            System.out.println("\nTrying to create an account into the wrong User");
            return null;
        }
    }
    public List<FinancialAccountResponseDTO> getAccountsByUser(Long userId){
        List<FinancialAccount> accounts=this.financialAccountRepository.findByUserId(userId);
        List<FinancialAccountResponseDTO> finalAccounts= new ArrayList<>();
        for(int i=0; i<accounts.size();i++){
            finalAccounts.add(this.toDto(accounts.get(i)));
        }
        return finalAccounts;
    }
    public FinancialAccountResponseDTO getFinancialAccountById(Long userId, Long financialAccountId){
        if(!this.userRepository.existsById(userId)){
            throw new RuntimeException("User doesnt exist");
        }
        FinancialAccount financialAccount = this.financialAccountRepository.findById(financialAccountId)
                .orElseThrow(() -> new RuntimeException("Account doesnt exist"));
        if(!userId.equals(financialAccount.getUser().getId())){
            throw new RuntimeException("Account doesnt belong to this user");
        }
        return toDto(financialAccount);
    }

    private FinancialAccountResponseDTO toDto(FinancialAccount financialAcount){
        FinancialAccountResponseDTO response= new FinancialAccountResponseDTO();
        response.setId(financialAcount.getId());
        response.setAmount(financialAcount.getAmount());
        response.setName(financialAcount.getName());
        response.setDate(LocalDate.now());
        return response;
    }

}
