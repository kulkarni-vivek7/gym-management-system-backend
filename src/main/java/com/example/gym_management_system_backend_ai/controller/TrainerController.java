package com.example.gym_management_system_backend_ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;
import com.example.gym_management_system_backend_ai.service.TrainerService;

@RestController
@RequestMapping("/trainer")
@CrossOrigin(origins = "http://localhost:5173/")
public class TrainerController {

	private final TrainerService trainerService;

	public TrainerController(TrainerService trainerService) {
		this.trainerService = trainerService;
	}
	
	@GetMapping("/details")
    public ResponseEntity<ResponseStructure<Trainer>> getTrainerDetails(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String email) {
        return trainerService.getTrainerDetails(email);
    }
	
	@GetMapping("/getAllRegisteredMembers")
    public ResponseEntity<ResponseStructure<?>> getAllRegisteredMembers(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String trainerEmail,
            @RequestParam Integer page,
            @RequestParam Integer limit) {
        return trainerService.getAllRegisteredMembers(trainerEmail, page, limit);
    }
}
