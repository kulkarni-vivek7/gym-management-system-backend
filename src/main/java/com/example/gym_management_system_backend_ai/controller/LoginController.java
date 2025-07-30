package com.example.gym_management_system_backend_ai.controller;

import com.example.gym_management_system_backend_ai.dto.AdminRegisterDto;
import com.example.gym_management_system_backend_ai.dto.LoginRequestDto;
import com.example.gym_management_system_backend_ai.entityDocument.User;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;
import com.example.gym_management_system_backend_ai.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<User>> register(@RequestBody AdminRegisterDto adminRegisterDto) {
        return loginService.adminRegister(adminRegisterDto);
    }

    @GetMapping("/send-otp-email")
    public ResponseEntity<ResponseStructure<String>> sendOtp(@RequestParam String email) {
        return loginService.sendOtp(email);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> login(@RequestBody LoginRequestDto loginRequestDto) {
        return loginService.loginWithOtp(loginRequestDto);
    }
}
