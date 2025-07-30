package com.example.gym_management_system_backend_ai.customExceptions;

public class MembershipAlreadyExistsException extends RuntimeException {
    public MembershipAlreadyExistsException(String message) {
        super(message);
    }
}
