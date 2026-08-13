package com.morteega.financialmanagement.model.users;

import java.util.List;

import com.morteega.financialmanagement.model.FinancialAccount;
import com.morteega.financialmanagement.model.Budget;

import jakarta.persistence.*;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false)
    private String email;
    private String password; //en AuthService es donde hasheo la password y la guardo como una columna normal en latabla pero hasehada
    @OneToMany(mappedBy="user")
    private List<FinancialAccount> financialAccounts;
    @OneToMany(mappedBy="user")
    private List<Budget> budgetList;
    
    public User(String email, String password, List<FinancialAccount> financialAccounts){
        this.email=email;
        this.password=password;
        this.financialAccounts=financialAccounts;
    }

    
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<FinancialAccount> getFinancialAccounts() {
        return financialAccounts;
    }

    public void setFinancialAccounts(List<FinancialAccount> financialAccounts) {
        this.financialAccounts = financialAccounts;
    }
    public List<Budget> getBudgets(){
        return this.budgetList;
    }
    public void setBudgets(List<Budget> budgetList){
        this.budgetList=budgetList;
    }

}
