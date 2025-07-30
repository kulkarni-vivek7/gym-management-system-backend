package com.example.gym_management_system_backend_ai.customExceptions;

public class OtpAlreadyUsedException extends RuntimeException {
    public OtpAlreadyUsedException(String message) { super(message); }
}