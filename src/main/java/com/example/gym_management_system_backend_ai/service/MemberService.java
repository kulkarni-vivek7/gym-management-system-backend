package com.example.gym_management_system_backend_ai.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.gym_management_system_backend_ai.entityDocument.Member;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;

@Service
public interface MemberService {

	ResponseEntity<ResponseStructure<Member>> getMemberDetails(String email);

}
