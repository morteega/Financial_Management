package com.morteega.financialmanagement.dtos;

import java.math.BigDecimal;

public class FinancialAccountRequestDTO {

    private Long userId;
    private BigDecimal amount;
    private String name;

    public FinancialAccountRequestDTO(Long userId, BigDecimal amount, String name){
        this.amount=amount;
        this.userId=userId;
        this.name=name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
