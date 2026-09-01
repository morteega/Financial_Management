package com.morteega.financialmanagement.model;

import java.math.BigDecimal;

import com.morteega.financialmanagement.model.users.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private BigDecimal goalAmmount;
    @Column(nullable=false)
    private BigDecimal actualAmmount;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Budget(String name, BigDecimal actualAmmount, BigDecimal goalAmmount, User user){
        this.name=name;
        this.actualAmmount=actualAmmount;
        this.goalAmmount=goalAmmount;
        this.user=user;
    }
    public Budget(){
        this.name=null;
        this.actualAmmount=null;
        this.goalAmmount=null;
        this.user=null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getgoalAmmount() {
        return goalAmmount;
    }

    public void setgoalAmmount(BigDecimal goalAmmount) {
        this.goalAmmount = goalAmmount;
    }

    public BigDecimal getactualAmmount() {
        return actualAmmount;
    }

    public void setactualAmmount(BigDecimal actualAmmount) {
        this.actualAmmount = actualAmmount;
    }
    public void setUser(User user){
        this.user=user;
    }
    public User getUser(){
        return this.user;
    }

}
