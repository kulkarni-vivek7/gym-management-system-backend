package com.example.gym_management_system_backend_ai.entityDocument;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.gym_management_system_backend_ai.utils.Roles;
import com.example.gym_management_system_backend_ai.utils.Status;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDetails {

	@Id
	private String id;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	private String otp;
	
	@Column(unique = true, nullable = true)
	private Long phno;
	
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private LocalDateTime timeStamp;
	
	private Boolean isLoggedIn;
}
