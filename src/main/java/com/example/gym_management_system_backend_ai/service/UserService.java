package com.example.gym_management_system_backend_ai.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.gym_management_system_backend_ai.dto.AddTrainerDto;
import com.example.gym_management_system_backend_ai.dto.AddMemberDto;
import com.example.gym_management_system_backend_ai.dto.AddMembershipDto;
import com.example.gym_management_system_backend_ai.entityDocument.Member;
import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.entityDocument.User;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;

@Service
public interface UserService {

	ResponseEntity<ResponseStructure<Membership>> addMembership(String jwt, AddMembershipDto dto);

	ResponseEntity<ResponseStructure<Trainer>> addTrainer(String membershipName, AddTrainerDto addTrainerDto);

	ResponseEntity<ResponseStructure<Member>> addMember(String membershipName, Long trainerId, AddMemberDto dto);
	
//	Get Methods ------------------------------------------------------------------------------

	ResponseEntity<ResponseStructure<?>> viewAllMemberships(String jwt, String searchParam, String searchValue,
			Integer limit, Integer page);

	ResponseEntity<ResponseStructure<?>> viewAllTrainers(String jwt, String searchParam, String searchValue,
			Integer limit, Integer page);

	ResponseEntity<ResponseStructure<?>> viewAllMembers(String searchParam, String searchValue, Integer limit,
			Integer page);

	ResponseEntity<ResponseStructure<User>> getUserDetails(String email);

	ResponseEntity<ResponseStructure<List<Membership>>> getAllActiveMemberships();

	ResponseEntity<ResponseStructure<List<Trainer>>> findAllActiveTrainersByMembershipName(String membershipName);

//	PUT methods -------------------------------------------------------------------------------
	
	ResponseEntity<ResponseStructure<User>> updateUser(User user, String adminEmail);

	ResponseEntity<ResponseStructure<Trainer>> updateTrainer(Trainer trainer, String trainerEmail,
			String membershipName);

	ResponseEntity<ResponseStructure<Member>> updateMember(Member member, String memberEmail, String membershipName,
			Long trainerId);

	ResponseEntity<ResponseStructure<Membership>> updateMembership(Membership membership);
	
//	Delete Method

	ResponseEntity<ResponseStructure<String>> deleteByRole(String deleteRole, String deleteValue);

}
