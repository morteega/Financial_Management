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
    
    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository=transactionRepository;
    }

    public void addTransaction(User user,TransactionRequestDTO transactionRequestDTO){//De momento hago un void pero claude me dice que haga el metodo de forma que me devuelva un TransactionResponseDTO con el id guardado ya de la transaccion una vez escrito en la BD
        FinancialAccount financialAccount= financialAccountRepository.findById(transactionRequestDTO.getFinancialAccountId())
                                            .orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        if(user.getId().equals(financialAccount.getUser().getId())){
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
        
    }
    public void removeTransaction(User user, FinancialAccount financialAccount,Transaction transaction){ //Aqui User es para un tema de manejo de excepciones, en este caso que exista la cuenta para ese user ants de intentar eliiminarla
        if(user.getId().equals(financialAccount.getUser().getId())){
            transactionRepository.deleteById(transaction.getId());
        }else
            throw new RuntimeException("The account trying to delete de transaction from doesn't exist");
        
    }

}
