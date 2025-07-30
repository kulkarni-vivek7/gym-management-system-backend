package com.example.gym_management_system_backend_ai.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gym_management_system_backend_ai.dto.AddTrainerDto;
import com.example.gym_management_system_backend_ai.dto.AddMemberDto;
import com.example.gym_management_system_backend_ai.dto.AddMembershipDto;
import com.example.gym_management_system_backend_ai.entityDocument.Member;
import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.entityDocument.User;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;
import com.example.gym_management_system_backend_ai.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/addMembership")
    public ResponseEntity<ResponseStructure<Membership>> addMembership(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AddMembershipDto dto
    ) {
        return userService.addMembership(jwt, dto);
    }
	
	@PostMapping("/addTrainer")
    public ResponseEntity<ResponseStructure<Trainer>> addTrainer(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String membershipName,
            @RequestBody AddTrainerDto addTrainerDto
    ) {
        return userService.addTrainer(membershipName, addTrainerDto);
    }
	
	@PostMapping("/addMember")
    public ResponseEntity<ResponseStructure<Member>> addMember(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String membershipName,
            @RequestParam Long trainerId,
            @RequestBody AddMemberDto dto
    ) {
        return userService.addMember(membershipName, trainerId, dto);
    }
	
//	GET Methods -----------------------------------------------------------------------------------
	
	@GetMapping("/viewAllMemberships")
    public ResponseEntity<ResponseStructure<?>> viewAllMemberships(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String searchParam,
            @RequestParam String searchValue,
            @RequestParam Integer limit,
            @RequestParam Integer page
    ) {
        return userService.viewAllMemberships(jwt, searchParam, searchValue, limit, page);
    }
	
	@GetMapping("/viewAllTrainers")
    public ResponseEntity<ResponseStructure<?>> viewAllTrainers(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String searchParam,
            @RequestParam String searchValue,
            @RequestParam Integer limit,
            @RequestParam Integer page
    ) {
        return userService.viewAllTrainers(jwt, searchParam, searchValue, limit, page);
    }
	
	@GetMapping("/viewAllMembers")
    public ResponseEntity<ResponseStructure<?>> viewAllMembers(
        @RequestHeader("Authorization") String jwt,
        @RequestParam String searchParam,
        @RequestParam String searchValue,
        @RequestParam Integer limit,
        @RequestParam Integer page
    ) {
        return userService.viewAllMembers(searchParam, searchValue, limit, page);
    }
	
	@GetMapping("/getAdminDetails")
    public ResponseEntity<ResponseStructure<User>> getUserDetails(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String email
    ) {
        return userService.getUserDetails(email);
    }
	
	@GetMapping("/getAllActiveMembershipsNoLimit")
    public ResponseEntity<ResponseStructure<List<Membership>>> getAllActiveMemberships(
            @RequestHeader("Authorization") String jwt
    ) {
        return userService.getAllActiveMemberships();
    }
	
	@GetMapping("/getAllActiveTrainerByMembershipId")
    public ResponseEntity<ResponseStructure<List<Trainer>>> getActiveTrainersByMembership(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String membershipName
    ) {
        return userService.findAllActiveTrainersByMembershipName(membershipName);
    }
	
//	PUT Methods --------------------------------------------------------------------------------
	
	@PutMapping
    public ResponseEntity<ResponseStructure<User>> updateUser(
            @RequestHeader("Authorization") String jwt,
            @RequestBody User user,
            @RequestParam String adminEmail
    ) {
        return userService.updateUser(user, adminEmail);
    }
	
	@PutMapping("/updateTrainer")
    public ResponseEntity<ResponseStructure<Trainer>> updateTrainer(
        @RequestHeader("Authorization") String jwt,
        @RequestBody Trainer trainer,
        @RequestParam String trainerEmail,
        @RequestParam String membershipName
    ) {
        return userService.updateTrainer(trainer, trainerEmail, membershipName);
    }
	
	@PutMapping("/updateMember")
    public ResponseEntity<ResponseStructure<Member>> updateMember(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Member member,
            @RequestParam String memberEmail,
            @RequestParam String membershipName,
            @RequestParam Long trainerId) {

        return userService.updateMember(member, memberEmail, membershipName, trainerId);
    }
	
	@PutMapping("/updateMembership")
    public ResponseEntity<ResponseStructure<Membership>> updateMembership(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Membership membership) {
        return userService.updateMembership(membership);
    }
	
//	DELETE Methods ----------------------------------------------------------------------------------
	
	@DeleteMapping("/deleteUsers")
    public ResponseEntity<ResponseStructure<String>> deleteByRole(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String deleteRole,
            @RequestParam String deleteValue) 
	{
        return userService.deleteByRole(deleteRole, deleteValue);
    }
}
