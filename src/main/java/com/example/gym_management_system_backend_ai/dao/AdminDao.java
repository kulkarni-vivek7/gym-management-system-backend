package com.example.gym_management_system_backend_ai.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.gym_management_system_backend_ai.entityDocument.User;

@Repository
public interface AdminDao {

	User saveAdmin(User user);

	Optional<User> findByEmail(String email);

	User updateAdmin(User user);
}
