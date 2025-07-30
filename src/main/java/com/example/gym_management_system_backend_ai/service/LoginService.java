package com.example.gym_management_system_backend_ai.service;

import com.example.gym_management_system_backend_ai.dto.AdminRegisterDto;
import com.example.gym_management_system_backend_ai.dto.LoginRequestDto;
import com.example.gym_management_system_backend_ai.entityDocument.User;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    ResponseEntity<ResponseStructure<String>> sendOtp(String email);
    
    ResponseEntity<ResponseStructure<String>> loginWithOtp(LoginRequestDto loginRequestDto);
    
    ResponseEntity<ResponseStructure<User>> adminRegister(AdminRegisterDto dto);
}
