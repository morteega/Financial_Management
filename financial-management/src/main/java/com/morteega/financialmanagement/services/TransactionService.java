package com.morteega.financialmanagement.services;

import org.springframework.stereotype.Service;
import com.morteega.financialmanagement.repositories.TransactionRepository;
import com.morteega.financialmanagement.dtos.TransactionRequestDTO;
import com.morteega.financialmanagement.model.users.User;
import com.morteega.financialmanagement.model.Transaction;
import com.morteega.financialmanagement.repositories.FinancialAccountRepository;
import com.morteega.financialmanagement.model.FinancialAccount;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private FinancialAccountRepository financialAccountRepository;
    private User user;//comprobar si esta loggeado o no
    
    public TransactionService(TransactionRepository transactionRepository, User user){
        this.transactionRepository=transactionRepository;
        this.user=user;
    }

    public void addTransaction(TransactionRequestDTO transactionRequestDTO){//De momento hago un void pero claude me dice que haga el metodo de forma que me devuelva un TransactionResponseDTO con el id guardado ya de la transaccion una vez escrito en la BD
        FinancialAccount financialAccount= financialAccountRepository.findById(transactionRequestDTO.getFinancialAccountId())
                                            .orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        Transaction transaction= new Transaction(transactionRequestDTO.getName(),
                                                transactionRequestDTO.getAmount(),
                                                transactionRequestDTO.getCategory(),
                                                transactionRequestDTO.getSource(),
                                                transactionRequestDTO.getMerchant(),
                                                transactionRequestDTO.getTransactionType(),
                                                transactionRequestDTO.getIsRecurring(),
                                                financialAccount);
        transactionRepository.save(transaction);
    }
    public void removeTransaction(Transaction transaction){
        transactionRepository.delete(transaction);
    }


}
