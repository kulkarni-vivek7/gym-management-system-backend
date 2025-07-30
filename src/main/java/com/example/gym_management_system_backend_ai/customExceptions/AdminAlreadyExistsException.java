package com.example.gym_management_system_backend_ai.customExceptions;

public class AdminAlreadyExistsException extends RuntimeException {
	
    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}
