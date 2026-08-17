package com.morteega.financialmanagement.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morteega.financialmanagement.dtos.FinancialAccountRequestDTO;
import com.morteega.financialmanagement.model.users.User;
import com.morteega.financialmanagement.services.FinancialAccountService;
import com.morteega.financialmanagement.dtos.FinancialAccountResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/financial-management")
public class FinancialAccountController {
    private final FinancialAccountService financialAccountService;

    public FinancialAccountController(FinancialAccountService financialAccountService){
        this.financialAccountService=financialAccountService;
    }

    @PostMapping
    public ResponseEntity <FinancialAccountResponseDTO> create(@RequestParam User user,@RequestBody FinancialAccountRequestDTO dto){
        FinancialAccountResponseDTO created=financialAccountService.createFinancialAccount(user,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
