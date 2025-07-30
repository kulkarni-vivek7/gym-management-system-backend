package com.example.gym_management_system_backend_ai.dao;

import com.example.gym_management_system_backend_ai.entityDocument.LoginDetails;
import com.example.gym_management_system_backend_ai.entityDocument.Membership;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface LoginDao {
    Optional<LoginDetails> findByEmail(String email);
    
    LoginDetails save(LoginDetails loginDetails);
    
    boolean existsByRoleAdmin();

	void updateLoginDetails(LoginDetails loginDetails);

	Optional<Membership> findByPhno(Long phno);

	void delete(LoginDetails loginDetails);
}
