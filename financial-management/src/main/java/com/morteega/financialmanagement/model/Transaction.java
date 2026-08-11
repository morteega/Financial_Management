package com.morteega.financialmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String name;
    @Embedded
    private Category category;
    private String merchant;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TransactionType transactionType;

    private boolean isRecurring;
    private String source;

    public Transaction(String name, Double amount, Category category){
        this.name=name;
        this.amount=amount;
        this.category=category;
    }

    public void setAmount(Double amount){
        this.amount=amount;
    }
    public Double getAmount(){
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
}
