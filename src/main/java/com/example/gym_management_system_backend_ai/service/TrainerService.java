package com.example.gym_management_system_backend_ai.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;

@Service
public interface TrainerService {

	ResponseEntity<ResponseStructure<Trainer>> getTrainerDetails(String email);

	ResponseEntity<ResponseStructure<?>> getAllRegisteredMembers(String trainerEmail, Integer page, Integer limit);

}
