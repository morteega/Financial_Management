package com.morteega.financialmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.morteega.financialmanagement.model.FinancialAccount;

public interface FinancialAccountRepository extends JpaRepository<FinancialAccount, Long>{
}
