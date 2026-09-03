package com.morteega.financialmanagement.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.morteega.financialmanagement.repositories.TransactionRepository;
import com.morteega.financialmanagement.repositories.UserRepository;


import com.morteega.financialmanagement.dtos.TransactionRequestDTO;
import com.morteega.financialmanagement.dtos.TransactionResponseDTO;
import com.morteega.financialmanagement.model.users.User;
import com.morteega.financialmanagement.model.Transaction;
import com.morteega.financialmanagement.model.TransactionType;
import com.morteega.financialmanagement.repositories.FinancialAccountRepository;
import com.morteega.financialmanagement.model.FinancialAccount;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private FinancialAccountRepository financialAccountRepository;
    private UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, FinancialAccountRepository financialAccountRepository, UserRepository userRepository){
        this.financialAccountRepository=financialAccountRepository;
        this.userRepository=userRepository;
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
        if(transaction.getTransactionType()==TransactionType.INCOME){
            financialAccount.setAmount(financialAccount.getAmount().add(transaction.getAmount()));
        }else{
            financialAccount.setAmount(financialAccount.getAmount().subtract(transaction.getAmount()));
        }
        this.financialAccountRepository.save(financialAccount);
        TransactionResponseDTO response= this.toDto(transaction);
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
    public TransactionResponseDTO getTransactionById(Long id, Long userId){
        if(!this.userRepository.existsById(userId)){
            throw new RuntimeException("Given User not found");
        }
        Transaction transaction= this.transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
        if(!transaction.getFinancialAccount().getUser().getId().equals(userId)){
            throw new RuntimeException("This transaction doesn't belong to the given User");
        }
        TransactionResponseDTO response= this.toDto(transaction); 
        return response;
    }
    @Transactional
    public TransactionResponseDTO deleteTransaction(Long id, Long financialAccountId){
        Transaction transaction= this.transactionRepository.findByFinancialAccountIdAndId(financialAccountId, id);
        if(transaction==null){
            throw new RuntimeException("Transaction not found");
        }
        TransactionResponseDTO transactionResponseDTO= this.toDto(transaction);
        this.transactionRepository.deleteByFinancialAccountIdAndId(financialAccountId, id);
        return transactionResponseDTO;
    }






    
    private TransactionResponseDTO toDto(Transaction transaction){
        TransactionResponseDTO transactionResponseDTO= new TransactionResponseDTO();
        transactionResponseDTO.setAmount(transaction.getAmount());
        transactionResponseDTO.setCategory(transaction.getCategory());
        transactionResponseDTO.setDate(transaction.getDate());
        transactionResponseDTO.setFinancialAccountId(transaction.getFinancialAccount().getId());
        transactionResponseDTO.setId(transaction.getId());
        transactionResponseDTO.setIsRecurring(transaction.getIsRecurring());
        transactionResponseDTO.setName(transaction.getName());
        transactionResponseDTO.setMerchant(transaction.getMerchant());
        transactionResponseDTO.setSource(transaction.getSource());
        transactionResponseDTO.setTransactionType(transaction.getTransactionType());
        return transactionResponseDTO;
    }

}
