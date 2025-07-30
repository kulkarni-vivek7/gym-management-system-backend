package com.example.gym_management_system_backend_ai.customExceptions;

public class InactiveAccountException extends RuntimeException {
    public InactiveAccountException(String message) { super(message); }
}