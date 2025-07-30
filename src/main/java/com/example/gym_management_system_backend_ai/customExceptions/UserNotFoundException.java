package com.example.gym_management_system_backend_ai.customExceptions;

public class UserNotFoundException extends RuntimeException {
	
    public UserNotFoundException(String message) {
        super(message);
    }
}
