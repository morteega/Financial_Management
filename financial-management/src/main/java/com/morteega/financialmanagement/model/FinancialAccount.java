package com.morteega.financialmanagement.model;

import com.morteega.financialmanagement.model.users.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.GenerationType;

@Entity
@Table(name="Accounts")
public class FinancialAccount {
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false) // esta columna se va a llamar "user_id" dentro de la tabla de FinancialAccounts
    // y ademas va a se el mismo valor que la primary key de Users, en este caso "id" de users
    private User user;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private BigDecimal amount;
    @Column(nullable=false)
    private String name;
    @OneToMany(mappedBy="financialAccount")
    private List<Transaction> transactionList; //Alomejor deberia hacer Singleton cuando vaya por la logica
    //Si no es singleton de las listas, de las instancias de los repositorios alomejor, ya lo vere mas adelante


    public FinancialAccount(String name, User user, BigDecimal amount){
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

}
