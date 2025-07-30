package com.example.gym_management_system_backend_ai.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.gym_management_system_backend_ai.customExceptions.MemberNotFoundException;
import com.example.gym_management_system_backend_ai.dao.MemberDao;
import com.example.gym_management_system_backend_ai.entityDocument.Member;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;
import com.example.gym_management_system_backend_ai.service.MemberService;

@Component
public class MemberServiceImpl implements MemberService {

	private final MemberDao memberDao;

	public MemberServiceImpl(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public ResponseEntity<ResponseStructure<Member>> getMemberDetails(String email) {
		
		Member member = memberDao.findMemberByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with email: " + email));

        ResponseStructure<Member> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Member details fetched successfully");
        response.setBody(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
}
