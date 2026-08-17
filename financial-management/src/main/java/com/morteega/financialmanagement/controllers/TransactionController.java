package com.morteega.financialmanagement.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morteega.financialmanagement.dtos.TransactionResponseDTO;
import com.morteega.financialmanagement.repositories.TransactionRepository;
import com.morteega.financialmanagement.services.TransactionService;
import com.morteega.financialmanagement.dtos.TransactionRequestDTO;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@RequestParam Long userId, @RequestBody TransactionRequestDTO transactionRequestDTO){
        TransactionResponseDTO created=this.transactionService.addTransaction(null, transactionRequestDTO);
    }
}
