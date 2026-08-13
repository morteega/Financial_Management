package com.morteega.financialmanagement.dtos;

import java.math.BigDecimal;

import com.morteega.financialmanagement.model.Category;
import com.morteega.financialmanagement.model.TransactionType;

public class TransactionRequestDTO {
    private BigDecimal amount;
    private String name;
    private Long financialAccountId;
    private Category category;
    private String merchant;
    private String source;
    private TransactionType transactionType;
    private boolean isRecurring;

    public TransactionRequestDTO(BigDecimal amount, String name, Long financialAccountId, Category category, String merchant, String source, TransactionType transactionType, boolean isIsRecurring){
        this.amount=amount;
        this.category=category;
        this.financialAccountId=financialAccountId;
        this.merchant=merchant;
        this.name=name;
        this.source=source;
        this.transactionType=transactionType;
        this.isRecurring=isIsRecurring;
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

    public Category getCategory(){
        return this.category;
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

    public boolean getIsRecurring() {
        return this.isRecurring;
    }

    public void setIsRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

}
