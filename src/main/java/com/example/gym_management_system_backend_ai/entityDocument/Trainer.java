package com.example.gym_management_system_backend_ai.entityDocument;


import com.example.gym_management_system_backend_ai.utils.Gender;
import com.example.gym_management_system_backend_ai.utils.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trainer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long registerNo;
	
	private Long trainerId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Integer age;
	
	@Column(nullable = false, unique = true)
	private Long phno;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private Double salary;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "membership_id", referencedColumnName = "id")
	private Membership membership;
	
	@Enumerated(EnumType.STRING)
	private Status status;
}
