package com.morteega.financialmanagement.dtos;

import java.math.BigDecimal;

public class BudgetRequestDTO {
    private String name;
    private BigDecimal goalAmmount;
    private BigDecimal actualAmmount;
    private Long userId;

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getGoalAmmount() {
        return goalAmmount;
    }

    public void setGoalAmmount(BigDecimal goalAmmount) {
        this.goalAmmount = goalAmmount;
    }

    public BigDecimal getActualAmmount() {
        return actualAmmount;
    }

    public void setActualAmmount(BigDecimal actualAmmount) {
        this.actualAmmount = actualAmmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
