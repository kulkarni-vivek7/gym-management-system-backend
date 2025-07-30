package com.example.gym_management_system_backend_ai.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.gym_management_system_backend_ai.entityDocument.User;

public interface AdminRepository extends MongoRepository<User, String> {

	Optional<User> findByEmail(String email);
}
