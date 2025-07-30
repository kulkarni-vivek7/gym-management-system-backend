package com.example.gym_management_system_backend_ai.customExceptions;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String message) {
        super(message);
    }
}
