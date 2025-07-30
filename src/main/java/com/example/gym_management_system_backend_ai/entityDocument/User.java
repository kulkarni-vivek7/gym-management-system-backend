package com.example.gym_management_system_backend_ai.entityDocument;


import org.springframework.data.mongodb.core.mapping.Document;

import com.example.gym_management_system_backend_ai.utils.Gender;
import com.example.gym_management_system_backend_ai.utils.Status;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class User {

	@Id
	private String id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Integer age;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender; 
	
	@Column(nullable = false)
	private Long phno;
	
	@Column(nullable = false)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Status status;
}
