package com.example.gym_management_system_backend_ai.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private Integer status;
    
    private String exceptionMessage;
}
