package com.morteega.financialmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.morteega.financialmanagement.model.users.User;
public interface UserRepository extends JpaRepository<User, Long>{
}
