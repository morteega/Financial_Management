package com.morteega.financialmanagement.repositories;

import com.morteega.financialmanagement.model.Transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    List<Transaction> findByFinancialAccountId(Long financialAccountId);
    Transaction findByFinancialAccountIdAndId(Long financialAccountId, Long transactionId);
    void deleteByFinancialAccountIdAndId(Long financialAccountId, Long transactionId);
}
