package com.morteega.financialmanagement.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.morteega.financialmanagement.repositories.TransactionRepository;

import jakarta.persistence.EntityNotFoundException;

import com.morteega.financialmanagement.dtos.TransactionRequestDTO;
import com.morteega.financialmanagement.dtos.TransactionResponseDTO;
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

    public TransactionResponseDTO addTransaction(Long userId,TransactionRequestDTO transactionRequestDTO){//De momento hago un void pero claude me dice que haga el metodo de forma que me devuelva un TransactionResponseDTO con el id guardado ya de la transaccion una vez escrito en la BD
        FinancialAccount financialAccount= financialAccountRepository.findById(transactionRequestDTO.getFinancialAccountId())
                                            .orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        if(userId.equals(financialAccount.getUser().getId())){
            Transaction transaction= new Transaction(transactionRequestDTO.getName(),
                                                transactionRequestDTO.getAmount(),
                                                transactionRequestDTO.getCategory(),
                                                transactionRequestDTO.getSource(),
                                                transactionRequestDTO.getMerchant(),
                                                transactionRequestDTO.getTransactionType(),
                                                transactionRequestDTO.getIsRecurring(),
                                                financialAccount);
        transactionRepository.save(transaction);
        TransactionResponseDTO response= new TransactionResponseDTO(transaction.getAmount(), transaction.getName(), transaction.getFinancialAccount().getId(),
                transaction.geCategory(), transaction.getMerchant(), transaction.getSource(), transaction.getTransactionType(),
                transaction.getIsRecurring(), transaction.getId());
                response.setDate(transaction.getDate());
        return response;
        }else
            throw new RuntimeException("User id does not match the necessary id to acces this account");
        
    }
    public void removeTransaction(User user, FinancialAccount financialAccount,Transaction transaction){ //Aqui User es para un tema de manejo de excepciones, en este caso que exista la cuenta para ese user ants de intentar eliiminarla
        if(user.getId().equals(financialAccount.getUser().getId())){
            transactionRepository.deleteById(transaction.getId());
        }else
            throw new RuntimeException("The account trying to delete de transaction from doesn't exist");
        
    }
    public List<TransactionResponseDTO> getAllTransactions(Long userId, Long financialAccounId){
        FinancialAccount account=this.financialAccountRepository.findById(financialAccounId).orElseThrow();
        if(!userId.equals(account.getUser().getId())){
            throw new RuntimeException("\nFinancialAccount userId does not match with provided userId");
        }else{
            List<Transaction> transactions= this.transactionRepository.findByFinancialAccountId(account.getId());
            List<TransactionResponseDTO> transactionResponseDTOs= new ArrayList<>();
            for(int i=0;i<transactions.size(); i++){
                transactionResponseDTOs.add(this.toDto(transactions.get(i)));
            }
            return transactionResponseDTOs;
        }
    }
    private TransactionResponseDTO toDto(Transaction transaction){
        TransactionResponseDTO transactionResponseDTO= new TransactionResponseDTO();
        transactionResponseDTO.setAmount(transaction.getAmount());
        transactionResponseDTO.setCategory(transaction.geCategory());
        transactionResponseDTO.setDate(transaction.getDate());
        transactionResponseDTO.setFinancialAccountId(transaction.getFinancialAccount().getId());
        transactionResponseDTO.setId(transaction.getId());
        transactionResponseDTO.setIsRecurring(transaction.getIsRecurring());
        transactionResponseDTO.setMerchant(transaction.getMerchant());
        transactionResponseDTO.setSource(transaction.getSource());
        transactionResponseDTO.setTransactionType(transaction.getTransactionType());
        return transactionResponseDTO;
    }

}
