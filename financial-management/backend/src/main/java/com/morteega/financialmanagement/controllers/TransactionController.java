package com.morteega.financialmanagement.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morteega.financialmanagement.dtos.TransactionResponseDTO;
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
        TransactionResponseDTO created=this.transactionService.addTransaction(userId, transactionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAll(@RequestParam Long userid, @RequestParam Long financialAccountId){
        return ResponseEntity.ok(this.transactionService.getAllTransactions(userid, financialAccountId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id, @RequestParam Long userId){
        return ResponseEntity.ok(this.transactionService.getTransactionById(id, userId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> deleteTransactionById(@PathVariable Long id, @RequestParam Long userId){
        return ResponseEntity.ok().body(this.transactionService.deleteTransactionById(id, userId));
    }
}
