package com.morteega.financialmanagement.services;

import org.springframework.stereotype.Service;
import com.morteega.financialmanagement.repositories.TransactionRepository;
import com.morteega.financialmanagement.model.users.User;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private User user;
    

}
