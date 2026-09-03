package com.morteega.financialmanagement.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morteega.financialmanagement.dtos.FinancialAccountRequestDTO;
import com.morteega.financialmanagement.services.FinancialAccountService;
import com.morteega.financialmanagement.dtos.FinancialAccountResponseDTO;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("api/financial-management")
public class FinancialAccountController {
    private final FinancialAccountService financialAccountService;

    public FinancialAccountController(FinancialAccountService financialAccountService){
        this.financialAccountService=financialAccountService;
    }

    @PostMapping
    public ResponseEntity <FinancialAccountResponseDTO> create(@RequestParam Long userId, @RequestBody FinancialAccountRequestDTO dto){
        FinancialAccountResponseDTO created=financialAccountService.createFinancialAccount(userId,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @GetMapping
    public ResponseEntity <List<FinancialAccountResponseDTO>> getAll(@RequestParam Long userId){
        List<FinancialAccountResponseDTO> financialAccounts=this.financialAccountService.getAllAccounts(userId);
        return ResponseEntity.ok(financialAccounts);
    }
    @GetMapping("/{accountId}")
    public ResponseEntity <FinancialAccountResponseDTO> getAccountById(@RequestParam Long userId, @PathVariable Long accountId){
        FinancialAccountResponseDTO account = this.financialAccountService.getFinancialAccount(userId, accountId);
        return ResponseEntity.ok(account);
    }
    @DeleteMapping("/{acountId}")
    public ResponseEntity<FinancialAccountResponseDTO> deleteAccount(@RequestParam Long financialAccountId){
        FinancialAccountResponseDTO accountDto=this.financialAccountService.deleteFinancialAccount(financialAccountId);
        return ResponseEntity.ok(accountDto);
    }
}


