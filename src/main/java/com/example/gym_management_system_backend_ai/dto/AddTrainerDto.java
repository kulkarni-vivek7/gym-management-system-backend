package com.example.gym_management_system_backend_ai.dto;

import com.example.gym_management_system_backend_ai.utils.Gender;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTrainerDto {
    private String name;
    
    private Integer age;
    
    private Long phno;
    
    private String email;
    
    private Double salary;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
