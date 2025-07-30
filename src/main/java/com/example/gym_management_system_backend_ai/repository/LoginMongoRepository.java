package com.example.gym_management_system_backend_ai.repository;

import com.example.gym_management_system_backend_ai.entityDocument.LoginDetails;
import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.utils.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LoginMongoRepository extends MongoRepository<LoginDetails, String> {
    Optional<LoginDetails> findFirstByEmail(String email);
    
    boolean existsByRole(Roles role);

	Optional<Membership> findFirstByPhno(Long phno);
}
