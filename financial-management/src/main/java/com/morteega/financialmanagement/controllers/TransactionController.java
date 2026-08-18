package com.morteega.financialmanagement.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService, TransactionRepository transactionRepository){
        this.transactionService=transactionService;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@RequestParam Long userId, @RequestBody TransactionRequestDTO transactionRequestDTO){
        TransactionResponseDTO created=this.transactionService.addTransaction(userId, transactionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAll(@RequestParam Long userid, @RequestParam Long financialAccountId){
        return ResponseEntity.ok(this.transactionService.getAllTransactions(userid, financialAccountId));
    }
}
