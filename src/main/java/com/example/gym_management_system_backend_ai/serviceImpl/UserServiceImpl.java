package com.example.gym_management_system_backend_ai.serviceImpl;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.gym_management_system_backend_ai.customExceptions.EmailAlreadyExistsException;
import com.example.gym_management_system_backend_ai.customExceptions.LoginDetailsNotFoundException;
import com.example.gym_management_system_backend_ai.customExceptions.MemberNotFoundException;
import com.example.gym_management_system_backend_ai.customExceptions.MembershipAlreadyExistsException;
import com.example.gym_management_system_backend_ai.customExceptions.MembershipNotFoundException;
import com.example.gym_management_system_backend_ai.customExceptions.PhoneAlreadyExistsException;
import com.example.gym_management_system_backend_ai.customExceptions.TrainerNotFoundException;
import com.example.gym_management_system_backend_ai.customExceptions.UpdateTrainerException;
import com.example.gym_management_system_backend_ai.customExceptions.UserNotFoundException;
import com.example.gym_management_system_backend_ai.dao.AdminDao;
import com.example.gym_management_system_backend_ai.dao.LoginDao;
import com.example.gym_management_system_backend_ai.dao.MemberDao;
import com.example.gym_management_system_backend_ai.dao.MembershipDao;
import com.example.gym_management_system_backend_ai.dao.TrainerDao;
import com.example.gym_management_system_backend_ai.dto.AddTrainerDto;
import com.example.gym_management_system_backend_ai.dto.AddMemberDto;
import com.example.gym_management_system_backend_ai.dto.AddMembershipDto;
import com.example.gym_management_system_backend_ai.entityDocument.LoginDetails;
import com.example.gym_management_system_backend_ai.entityDocument.Member;
import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.entityDocument.User;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;
import com.example.gym_management_system_backend_ai.service.UserService;
import com.example.gym_management_system_backend_ai.utils.Roles;
import com.example.gym_management_system_backend_ai.utils.Status;

@Component
public class UserServiceImpl implements UserService {

	private final MembershipDao membershipDao;
	private final LoginDao loginDao;
	private final TrainerDao trainerDao;
	private final MemberDao memberDao;
	private final AdminDao adminDao;

	public UserServiceImpl(MembershipDao membershipDao, LoginDao loginDao, TrainerDao trainerDao, MemberDao memberDao, AdminDao adminDao) {
		this.membershipDao = membershipDao;
		this.loginDao = loginDao;
		this.trainerDao = trainerDao;
		this.memberDao = memberDao;
		this.adminDao = adminDao;
	}

	@Override
	public ResponseEntity<ResponseStructure<Membership>> addMembership(String jwt, AddMembershipDto dto) {
		// TODO Auto-generated method stub
		membershipDao.findByNameContainingIgnoreCase(dto.getName()).ifPresent(existing -> {
            throw new MembershipAlreadyExistsException("Membership Has Already Registered With The Given Name");
        });

        Membership membership = new Membership();
        membership.setName(dto.getName());
        membership.setDuration(dto.getDuration());
        membership.setPrice(dto.getPrice());
        membership.setStatus(Status.ACTIVE);

        Membership saved = membershipDao.save(membership);

        ResponseStructure<Membership> response = new ResponseStructure<>(
                HttpStatus.CREATED.value(),
                "Membership created successfully",
                saved
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<Trainer>> addTrainer(String membershipName, AddTrainerDto addTrainerDto) {
		// TODO Auto-generated method stub
		loginDao.findByEmail(addTrainerDto.getEmail()).ifPresent(e -> {
            throw new EmailAlreadyExistsException("Email Already In Use...");
        });

        loginDao.findByPhno(addTrainerDto.getPhno()).ifPresent(p -> {
            throw new PhoneAlreadyExistsException("Phone number Already In Use...");
        });

        Membership membership = membershipDao.findByNameContainingIgnoreCase(membershipName)
                .orElseThrow(() -> new MembershipNotFoundException("Membership not found"));

        Trainer trainer = new Trainer();
        trainer.setName(addTrainerDto.getName());
        trainer.setAge(addTrainerDto.getAge());
        trainer.setEmail(addTrainerDto.getEmail());
        trainer.setPhno(addTrainerDto.getPhno());
        trainer.setSalary(addTrainerDto.getSalary());
        trainer.setGender(addTrainerDto.getGender());
        trainer.setMembership(membership);
        trainer.setStatus(Status.ACTIVE);

        Trainer savedTrainer = trainerDao.save(trainer);

        LoginDetails login = new LoginDetails();
        login.setEmail(savedTrainer.getEmail());
        login.setPhno(savedTrainer.getPhno());
        login.setRole(Roles.TRAINER);
        login.setStatus(Status.ACTIVE);

        loginDao.updateLoginDetails(login);
        
        List<Trainer> trainers = trainerDao.findAllTrainersAsceOrder();
		
		Long trainerId = 1L;
		
		for (Trainer trainer2 : trainers) {
			
			trainer2.setTrainerId(trainerId++);
		}
		
		trainerDao.saveAllTrainers(trainers);
		
		trainer = trainerDao.findTrainerByEmail(trainer.getEmail()).orElseThrow(() -> new
				TrainerNotFoundException("Invalid Trainer Email Id... Unable to Find Trainer Details"));

        ResponseStructure<Trainer> response = new ResponseStructure<>(
                HttpStatus.CREATED.value(),
                "Trainer added successfully",
                trainer
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<Member>> addMember(String membershipName, Long trainerId,
			AddMemberDto dto) {
		// TODO Auto-generated method stub
		loginDao.findByEmail(dto.getEmail()).ifPresent(e -> {
            throw new EmailAlreadyExistsException("Email Already In Use...");
        });

        loginDao.findByPhno(dto.getPhno()).ifPresent(p -> {
            throw new PhoneAlreadyExistsException("Phone number Already In Use...");
        });

        Membership membership = membershipDao.findByNameContainingIgnoreCase(membershipName)
                .orElseThrow(() -> new UserNotFoundException("Membership not found"));

        Trainer trainer = trainerDao.findById(trainerId)
                .orElseThrow(() -> new UserNotFoundException("Trainer not found"));

        Member member = new Member();
        member.setName(dto.getName());
        member.setAge(dto.getAge());
        member.setPhno(dto.getPhno());
        member.setEmail(dto.getEmail());
        member.setGender(dto.getGender());
        member.setMembership(membership);
        member.setTrainer(trainer);
        member.setStatus(Status.ACTIVE);

        Member savedMember = memberDao.save(member);

        LoginDetails login = new LoginDetails();
        login.setEmail(savedMember.getEmail());
        login.setPhno(savedMember.getPhno());
        login.setRole(Roles.MEMBER);
        login.setStatus(savedMember.getStatus());
        login.setIsLoggedIn(false);

        loginDao.updateLoginDetails(login);
        
        List<Member> members = memberDao.findAllMembersAsceOrder();
		
		Long memberId = 1L;
		
		for (Member member2 : members) {
			
			member2.setMemberId(memberId++);
		}
		
		memberDao.saveAllMembers(members);
		
		member = memberDao.findMemberByEmail(member.getEmail()).orElseThrow(() -> new
				MemberNotFoundException("Invalid Member Email Id... Unable to Find Member Details"));

        ResponseStructure<Member> response = new ResponseStructure<>(
                HttpStatus.CREATED.value(),
                "Member added successfully",
                member
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	
//	Get Methods -------------------------------------------------------------------------------------
	
	@Override
	public ResponseEntity<ResponseStructure<?>> viewAllMemberships(String jwt, String searchParam, String searchValue,
			Integer limit, Integer page) 
	{
		Pageable pageable = PageRequest.of(page, limit, Sort.by("name").ascending());

        if ("name".equalsIgnoreCase(searchParam)) {
            Membership membership = membershipDao.findByNameContainingIgnoreCase(searchValue.toUpperCase())
                    .orElseThrow(() -> new MembershipNotFoundException("No Membership Found for name: " + searchValue.toUpperCase()));

            ResponseStructure<Membership> response = new ResponseStructure<>(
                    HttpStatus.OK.value(),
                    "Membership found by name",
                    membership
            );
            return new ResponseEntity<>(response, HttpStatus.OK);

        } 
        else if ("duration".equalsIgnoreCase(searchParam)) {
            Page<Membership> memberships = membershipDao.findByDurationAndStatus(searchValue, Status.ACTIVE, pageable);

            if (memberships.isEmpty()) {
                throw new MembershipNotFoundException("No Membership Details Found Given Duration");
            }

            ResponseStructure<Page<Membership>> response = new ResponseStructure<>(
                    HttpStatus.OK.value(),
                    "Memberships found by duration",
                    memberships
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } 
        else if ("inactive".equalsIgnoreCase(searchValue)) {
        	
        	Page<Membership> inactiveMemberships = membershipDao.findAllByStatus(Status.INACTIVE, pageable);

            if (inactiveMemberships.isEmpty()) {
            	throw new MembershipNotFoundException("No Inactive Memberships Found");
            }

            ResponseStructure<Page<Membership>> response = new ResponseStructure<>(
            		HttpStatus.OK.value(),
            		"All Inactive Memberships Found Successfully",
            		inactiveMemberships
            		);
            return new ResponseEntity<>(response, HttpStatus.OK);
		}

        Page<Membership> memberships = membershipDao.findAllByStatus(Status.ACTIVE, pageable);

        if (memberships.isEmpty()) {
        	throw new MembershipNotFoundException("No Active Memberships Found");
        }

        ResponseStructure<Page<Membership>> response = new ResponseStructure<>(
        		HttpStatus.OK.value(),
        		"All Active Memberships Found Successfully",
                memberships
        		);
        return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<?>> viewAllTrainers(String jwt, String searchParam, String searchValue,
			Integer limit, Integer page) {
		Pageable pageable = PageRequest.of(page, limit, Sort.by("trainerId").ascending());

        if ("trainerId".equalsIgnoreCase(searchParam)) {
            Long id = Long.valueOf(searchValue);
            Trainer trainer = trainerDao.findById(id)
                    .orElseThrow(() -> new TrainerNotFoundException("Trainer not found for ID: " + id));
            return ResponseEntity.ok(new ResponseStructure<>(HttpStatus.OK.value(), "Trainer found", trainer));

        } 
        else if ("email".equalsIgnoreCase(searchParam)) {
            Trainer trainer = trainerDao.findTrainerByEmail(searchValue)
                    .orElseThrow(() -> new TrainerNotFoundException("Trainer not found for email: " + searchValue));
            return ResponseEntity.ok(new ResponseStructure<>(HttpStatus.OK.value(), "Trainer found", trainer));

        } 
        else if ("name".equalsIgnoreCase(searchParam)) {
            Page<Trainer> trainers = trainerDao.findByNameContainingIgnoreCaseAndStatus(searchValue, Status.ACTIVE, pageable);
            if (trainers.isEmpty()) throw new TrainerNotFoundException("No Trainers found with name: " + searchValue);
            return ResponseEntity.ok(new ResponseStructure<>(HttpStatus.OK.value(), "Trainers found by name", trainers));

        } 
        else if ("membershipName".equalsIgnoreCase(searchParam)) {
            Membership membership = membershipDao.findByNameContainingIgnoreCase(searchValue.toUpperCase())
                    .orElseThrow(() -> new TrainerNotFoundException("No membership found with name: " + searchValue.toUpperCase()));
            
            Page<Trainer> trainers = trainerDao.findByMembershipAndStatus(membership, Status.ACTIVE, pageable);
            
            if (trainers.isEmpty()) throw new TrainerNotFoundException("No Trainers found under membership: " + searchValue);
            return ResponseEntity.ok(new ResponseStructure<>(HttpStatus.OK.value(), "Trainers by membership", trainers));

        } 
        else if ("inactive".equalsIgnoreCase(searchValue)) {
            Page<Trainer> inactiveTrainers = trainerDao.findAllByStatus(Status.INACTIVE, pageable);
            if (inactiveTrainers.isEmpty()) throw new TrainerNotFoundException("No inactive trainers found");
            return ResponseEntity.ok(new ResponseStructure<>(HttpStatus.OK.value(), "Inactive trainers found", inactiveTrainers));

        } 
        else {
            Page<Trainer> allActive = trainerDao.findAllByStatus(Status.ACTIVE, pageable);
            if (allActive.isEmpty()) throw new TrainerNotFoundException("No active trainers found");
            return ResponseEntity.ok(new ResponseStructure<>(HttpStatus.OK.value(), "All active trainers", allActive));
        }
	}

	@Override
	public ResponseEntity<ResponseStructure<?>> viewAllMembers(String searchParam, String searchValue, Integer limit,
			Integer page) {
		Pageable pageable = PageRequest.of(page, limit, Sort.by("memberId").ascending());

        switch (searchParam.toLowerCase()) {
            case "memberid": {
                Optional<Member> optional = memberDao.findByMemberId(Long.valueOf(searchValue));
                if (optional.isEmpty()) {
                    throw new MemberNotFoundException("No Member found with ID: " + searchValue);
                }
                return successResponse("Member found successfully", optional.get());
            }

            case "email": {
                Optional<Member> optional = memberDao.findMemberByEmail(searchValue);
                if (optional.isEmpty()) {
                    throw new MemberNotFoundException("No Member found with email: " + searchValue);
                }
                return successResponse("Member found successfully", optional.get());
            }

            case "name": {
                Page<Member> members = memberDao.findByNameContainingIgnoreCase(searchValue, pageable);
                if (members.isEmpty()) {
                    throw new MemberNotFoundException("No Members found with name: " + searchValue);
                }
                return successResponse("Members with name found successfully", members);
            }

            case "trainerid": {
                Trainer trainer = trainerDao.findById(Long.valueOf(searchValue))
                        .orElseThrow(() -> new MemberNotFoundException("Trainer not found with ID: " + searchValue));
                Page<Member> members = memberDao.findByTrainer(trainer, pageable);
                if (members.isEmpty()) {
                    throw new MemberNotFoundException("No Members found under trainer ID: " + searchValue);
                }
                return successResponse("Members under trainer found successfully", members);
            }

            case "membershipname": {
                Membership membership = membershipDao.findByNameContainingIgnoreCase(searchValue.toUpperCase())
                        .orElseThrow(() -> new MemberNotFoundException("Membership not found with name: " + searchValue.toUpperCase()));
                
                Page<Member> members = memberDao.findByMembership(membership, pageable);
                if (members.isEmpty()) {
                    throw new MemberNotFoundException("No Members found under membership name: " + searchValue);
                }
                return successResponse("Members under membership found successfully", members);
            }

            default: {
                if ("inactive".equalsIgnoreCase(searchValue)) {
                    Page<Member> members = memberDao.findAllByStatus(Status.INACTIVE, pageable);
                    if (members.isEmpty()) {
                        throw new MemberNotFoundException("No Inactive Members found");
                    }
                    return successResponse("Inactive members fetched successfully", members);
                } else {
                    Page<Member> members = memberDao.findAllByStatus(Status.ACTIVE, pageable);
                    if (members.isEmpty()) {
                        throw new MemberNotFoundException("No Active Members found");
                    }
                    return successResponse("Active members fetched successfully", members);
                }
            }
        }
	}
	
	private <T> ResponseEntity<ResponseStructure<?>> successResponse(String message, T body) {
        ResponseStructure<T> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(message);
        response.setBody(body);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

	@Override
	public ResponseEntity<ResponseStructure<User>> getUserDetails(String email) {
		User user = adminDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No User found with email: " + email));
		
		ResponseStructure<User> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("User details retrieved successfully");
        response.setBody(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Membership>>> getAllActiveMemberships() {
		
		List<Membership> memberships = membershipDao.findAllByStatus(Status.ACTIVE);
        if (memberships.isEmpty()) {
            throw new MembershipNotFoundException("No active memberships found");
        }

        ResponseStructure<List<Membership>> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Active memberships fetched successfully");
        response.setBody(memberships);

        return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Trainer>>> findAllActiveTrainersByMembershipName(
			String membershipName) {
		
		Membership membership = membershipDao.findByNameContainingIgnoreCase(membershipName.toUpperCase())
                .orElseThrow(() -> new MembershipNotFoundException("Membership not found for name: " + membershipName));

        List<Trainer> trainers = trainerDao.findAllByMembershipAndStatus(membership, Status.ACTIVE);

        ResponseStructure<List<Trainer>> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Active trainers found for membership: " + membershipName);
        response.setBody(trainers);

        return new ResponseEntity<>(response, HttpStatus.OK);

	}

//	PUT methods ---------------------------------------------------------------------------
	
	@Override
	public ResponseEntity<ResponseStructure<User>> updateUser(User user, String adminEmail) {
		User savedUser = adminDao.updateAdmin(user);

        LoginDetails loginDetails = loginDao.findByEmail(adminEmail)
                .orElseThrow(() -> new UserNotFoundException("Admin not found with email: " + adminEmail));

        loginDetails.setEmail(savedUser.getEmail());
        loginDetails.setPhno(savedUser.getPhno());
        loginDetails.setStatus(savedUser.getStatus());
        loginDao.updateLoginDetails(loginDetails);

        ResponseStructure<User> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Admin Details updated successfully");
        response.setBody(savedUser);

        return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<Trainer>> updateTrainer(Trainer trainer,
			String trainerEmail, String membershipName) {
		List<Member> members = memberDao.findByTrainer_RegisterNo(trainer.getRegisterNo());

        Trainer trainer1 = trainerDao.findTrainerByEmail(trainerEmail).orElseThrow(() -> new TrainerNotFoundException("Not able to find Trainer for given email id"));
        
        if (!trainer1.getMembership().getName().equals(membershipName) && !members.isEmpty()) {
            throw new UpdateTrainerException("Some Members Registered With This Trainer For The Previous Membership... Unable To Update Membership Details... Assign a new Trainer For Those Members To Do This Operation");
        }

        // Check for INACTIVE status update while members are still assigned
        if (trainer.getStatus().equals(Status.INACTIVE) && !members.isEmpty()) {
            throw new UpdateTrainerException("This Trainer is Assigned to Some Members... Unable to Update Status To INACTIVE... Delete or Assign Another Trainer To Those Members To Update The Status Of This Trainer");
        }

        // Fetch new membership
        Membership membership = membershipDao.findByNameContainingIgnoreCase(membershipName)
            .orElseThrow(() -> new MembershipNotFoundException("Membership not found with name: " + membershipName));
        trainer.setMembership(membership);

        // Save trainer
        Trainer saveTrainer = trainerDao.save(trainer);

        // Update login details
        LoginDetails loginDetails = loginDao.findByEmail(trainerEmail)
            .orElseThrow(() -> new LoginDetailsNotFoundException("Login details not found for trainer: " + trainerEmail));
        loginDetails.setEmail(saveTrainer.getEmail());
        loginDetails.setPhno(saveTrainer.getPhno());
        loginDetails.setStatus(saveTrainer.getStatus());
        loginDao.updateLoginDetails(loginDetails);

        ResponseStructure<Trainer> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Trainer Details updated successfully");
        response.setBody(saveTrainer);
        
        return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<ResponseStructure<Member>> updateMember(Member member, String memberEmail,
			String membershipName, Long trainerId) {
		// Fetch membership
        Membership membership = membershipDao.findByNameContainingIgnoreCase(membershipName)
                .orElseThrow(() -> new UserNotFoundException("Membership not found with name: " + membershipName));

        // Fetch trainer
        Trainer trainer = trainerDao.findById(trainerId)
                .orElseThrow(() -> new UserNotFoundException("Trainer not found with ID: " + trainerId));

        // Update associations
        member.setMembership(membership);
        member.setTrainer(trainer);

        // Save member
        Member savedMember = memberDao.save(member);

        // Update login details
        LoginDetails loginDetails = loginDao.findByEmail(memberEmail)
                .orElseThrow(() -> new UserNotFoundException("LoginDetails not found for member: " + memberEmail));

        loginDetails.setEmail(savedMember.getEmail());
        loginDetails.setPhno(savedMember.getPhno());
        loginDetails.setStatus(savedMember.getStatus());
        
        loginDao.save(loginDetails);

        ResponseStructure<Member> response = new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Member updated successfully",
                savedMember
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<Membership>> updateMembership(Membership membership) {
		// Check if setting to INACTIVE
        if (membership.getStatus() == Status.INACTIVE) {

            // Fetch related active trainers
            List<Trainer> activeTrainers = trainerDao.findAllByMembershipAndStatus(membership, Status.ACTIVE);
            
            if (!activeTrainers.isEmpty()) {
                throw new RuntimeException("Some Trainers Are Working For This Membership... Unable to Update Membership Status To INACTIVE... Delete or Assign Another Membership To Those Trainers To Update The Status Of This Membership");
            }

            // Fetch related members
            List<Member> members = memberDao.findByMembershipId(membership.getId());
            
            if (!members.isEmpty()) {
                throw new RuntimeException("This Membership Is Taken By Some Members... Unable to Update Membership Status To INACTIVE... Delete or Assign Another Membership To Those Members To Update The Status Of This Membership");
            }
        }

        // Save updated membership
        Membership savedMembership = membershipDao.save(membership);

        ResponseStructure<Membership> response = new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Membership updated successfully",
                membership
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
//	Delete Method-------------------------------------------------------------------------------------------

	@Override
	public ResponseEntity<ResponseStructure<String>> deleteByRole(String deleteRole, String deleteValue) {
		if (deleteRole.equalsIgnoreCase("trainer")) {
	        Trainer trainer = trainerDao.findTrainerByEmail(deleteValue)
	                .orElseThrow(() -> new UserNotFoundException("Trainer not found with email: " + deleteValue));

	        if (trainer.getStatus() == Status.ACTIVE)
	            throw new RuntimeException("Trainer is ACTIVE... Unable to Delete Trainer");

	        trainerDao.delete(trainer);

	        LoginDetails loginDetails = loginDao.findByEmail(deleteValue)
	                .orElseThrow(() -> new UserNotFoundException("LoginDetails not found for trainer email: " + deleteValue));
	        
	        loginDao.delete(loginDetails);

	        return new ResponseEntity<>(
	                new ResponseStructure<>(HttpStatus.OK.value(), "Trainer and login details deleted successfully", null),
	                HttpStatus.OK);

	    } 
		else if (deleteRole.equalsIgnoreCase("member")) {
	        Member member = memberDao.findMemberByEmail(deleteValue)
	                .orElseThrow(() -> new UserNotFoundException("Member not found with email: " + deleteValue));

	        if (member.getStatus() == Status.ACTIVE)
	            throw new RuntimeException("Member is ACTIVE... Unable to Delete Member");

	        memberDao.delete(member);

	        LoginDetails loginDetails = loginDao.findByEmail(deleteValue)
	                .orElseThrow(() -> new UserNotFoundException("LoginDetails not found for member email: " + deleteValue));
	        loginDao.delete(loginDetails);

	        return new ResponseEntity<>(
	                new ResponseStructure<>(HttpStatus.OK.value(), "Member and login details deleted successfully", null),
	                HttpStatus.OK);

	    } 
		else {
	        Membership membership = membershipDao.findByNameContainingIgnoreCase(deleteValue.toUpperCase())
	                .orElseThrow(() -> new UserNotFoundException("Membership not found with name: " + deleteValue));

	        if (membership.getStatus() == Status.ACTIVE)
	            throw new RuntimeException("Membership is ACTIVE... Unable to Delete Membership");

	        membershipDao.delete(membership);

	        return new ResponseEntity<>(
	                new ResponseStructure<>(HttpStatus.OK.value(), "Membership deleted successfully", null),
	                HttpStatus.OK);
	    }
	}
	
	
}
