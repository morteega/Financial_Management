package com.morteega.financialmanagement.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private BigDecimal amount;
    @Column(nullable=false)
    private String name;
    @Embedded
    @Column(nullable=false)
    private Category category;
    private String merchant;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TransactionType transactionType;
    @Column(nullable=false)
    private boolean isRecurring;
    @Column(nullable=false)
    private String source;
    @ManyToOne
    @JoinColumn(name="finacial_account_id" , nullable=false)
    private FinancialAccount financialAccount;
    

    public Transaction(String name, BigDecimal amount, Category category, String source, String merchant, TransactionType transactionType, boolean isRecurring, FinancialAccount financialAccount){
        this.name=name;
        this.amount=amount;
        this.category=category;
        this.source=source;
        this.merchant=merchant;
        this.transactionType=transactionType;
        this.isRecurring=isRecurring;
        this.financialAccount=financialAccount;
    }

    public void setAmount(BigDecimal amount){
        this.amount=amount;
    }
    public BigDecimal getAmount(){
        return this.amount;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
    public void setCategory(Category category){
        this.category=category;
    }
    public Category geCategory(){
        return this.category;
    }
    public TransactionType getTransactionType(){
        return this.transactionType;
    }
    public void setTransactionType(TransactionType transactionType){
        this.transactionType=transactionType;
    }
    public Long getId(){
        return this.id;
    }
    public void setIsRecurring(boolean isRecurring){
        this.isRecurring=isRecurring;
    }
    public boolean getIsRecurring(){
        return this.isRecurring;
    }
    public String getMerchant(){
        return this.merchant;
    }
    public void setMerchant(String merchant){
        this.merchant=merchant;
    }
    public String getSource(){
        return this.source;
    }
    public void setSource(String source){
        this.source=source;
    }
}
