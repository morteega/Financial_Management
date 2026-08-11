package com.morteega.financialmanagement.model;

import com.morteega.financialmanagement.model.users.*;
import jakarta.persistence.*;

import jakarta.persistence.GenerationType;

@Entity
@Table(name="Accounts")
public class FinancialAccount {
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false) // esta columna se va a llamar "user_id" dentro de la tabla de FinancialAccounts
    // y ademas va a se el mismo valor que la primary key, en este caso "id" de users
    private User user;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private Double amount;
    @Column(nullable=false)
    private String name;


    public FinancialAccount(String name, User user, Double amount){
        this.name=name;
        this.user=user;
        this.amount=amount;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Long getId() {
        return id;
    } 

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
