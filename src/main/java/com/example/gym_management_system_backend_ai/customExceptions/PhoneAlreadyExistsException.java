package com.example.gym_management_system_backend_ai.customExceptions;

public class PhoneAlreadyExistsException extends RuntimeException {
    public PhoneAlreadyExistsException(String message) {
        super(message);
    }
}
