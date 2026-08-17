package com.morteega.financialmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.morteega.financialmanagement.model.FinancialAccount;

public interface FinancialAccountRepository extends JpaRepository<FinancialAccount, Long>{
    public List<FinancialAccount> findByUserId(Long userId);
}
