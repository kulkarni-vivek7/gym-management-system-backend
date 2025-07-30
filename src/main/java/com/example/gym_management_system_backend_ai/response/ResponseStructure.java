package com.example.gym_management_system_backend_ai.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStructure<T> {
    private Integer status;
    
    private String message;
    
    private T body;
}
