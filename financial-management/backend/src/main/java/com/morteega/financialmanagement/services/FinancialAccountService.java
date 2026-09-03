package com.morteega.financialmanagement.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.morteega.financialmanagement.dtos.FinancialAccountRequestDTO;
import com.morteega.financialmanagement.dtos.FinancialAccountResponseDTO;
import com.morteega.financialmanagement.repositories.FinancialAccountRepository;
import com.morteega.financialmanagement.repositories.TransactionRepository;
import com.morteega.financialmanagement.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import com.morteega.financialmanagement.model.users.User;
import com.morteega.financialmanagement.model.FinancialAccount;

@Service
public class FinancialAccountService {
    private FinancialAccountRepository financialAccountRepository;
    private UserRepository userRepository;
    private TransactionRepository transactionRepository;

    public FinancialAccountService(FinancialAccountRepository financialAccountRepository, UserRepository userRepository, TransactionRepository transactionRepository){
        this.financialAccountRepository=financialAccountRepository;
        this.userRepository=userRepository;
        this.transactionRepository=transactionRepository;
    }

    public FinancialAccountResponseDTO createFinancialAccount(Long userId,FinancialAccountRequestDTO financialAccountRequestDTO){
        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        FinancialAccount financialAccount = new FinancialAccount();
        financialAccount.setAmount(financialAccountRequestDTO.getAmount());
        financialAccount.setName(financialAccountRequestDTO.getName());
        financialAccount.setUser(user);

        this.financialAccountRepository.save(financialAccount);
        return toDto(financialAccount);
    }
    public List<FinancialAccountResponseDTO> getAllAccounts(Long userId){
        List<FinancialAccount> accounts=this.financialAccountRepository.findByUserId(userId);
        List<FinancialAccountResponseDTO> finalAccounts= new ArrayList<>();
        for(int i=0; i<accounts.size();i++){
            finalAccounts.add(this.toDto(accounts.get(i)));
        }
        return finalAccounts;
    }
    public FinancialAccountResponseDTO getFinancialAccount(Long userId, Long financialAccountId){
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
    @Transactional
    public FinancialAccountResponseDTO deleteFinancialAccount(Long financialAccountId){
        FinancialAccount financialAccount=this.financialAccountRepository.findById(financialAccountId).orElseThrow(()-> new RuntimeException("Account not found"));
        FinancialAccountResponseDTO finanAccountResponseDTO= this.toDto(financialAccount);
        this.transactionRepository.deleteAll(this.transactionRepository.findByFinancialAccountId(financialAccountId));
        this.financialAccountRepository.deleteById(financialAccountId);
        return finanAccountResponseDTO;
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
