package com.example.gym_management_system_backend_ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gym_management_system_backend_ai.entityDocument.Member;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;
import com.example.gym_management_system_backend_ai.service.MemberService;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:5173/")
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/details")
    public ResponseEntity<ResponseStructure<Member>> getMemberDetails(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String email) {
        return memberService.getMemberDetails(email);
    }
}
