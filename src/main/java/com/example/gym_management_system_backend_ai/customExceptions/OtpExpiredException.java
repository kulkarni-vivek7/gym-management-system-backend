package com.example.gym_management_system_backend_ai.customExceptions;

public class OtpExpiredException extends RuntimeException {
    public OtpExpiredException(String message) { super(message); }
}