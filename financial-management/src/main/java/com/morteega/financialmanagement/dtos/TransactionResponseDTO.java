package com.morteega.financialmanagement.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.morteega.financialmanagement.model.TransactionType;
import com.morteega.financialmanagement.model.Category;



public class TransactionResponseDTO {
    private BigDecimal amount;
    private String name;
    private Long financialAccountId;
    private Category category;
    private String merchant;
    private String source;
    private TransactionType transactionType;
    private boolean isRecurring;
    private Long id;
    private LocalDate date;
    

    public TransactionResponseDTO(BigDecimal amount, String name, Long financialAccountId, Category category, String merchant, String source, TransactionType transactionType, boolean isIsRecurring, Long id, LocalDate date){
        this.amount=amount;
        this.category=category;
        this.financialAccountId=financialAccountId;
        this.merchant=merchant;
        this.name=name;
        this.source=source;
        this.transactionType=transactionType;
        this.isRecurring=isIsRecurring;
        this.date=date;
        this.id=id;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public boolean isIsRecurring() {
        return isRecurring;
    }
    public void setIsRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }
    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setDate(LocalDate date){
        this.date=date;
    }
    public LocalDate getDate(){
        return this.date;
    }

}

