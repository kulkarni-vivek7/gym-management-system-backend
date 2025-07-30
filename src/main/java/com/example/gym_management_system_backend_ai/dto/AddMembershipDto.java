package com.example.gym_management_system_backend_ai.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMembershipDto {
    private String name;
    private String duration;
    private Double price;
}
