package com.example.gym_management_system_backend_ai.serviceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.gym_management_system_backend_ai.customExceptions.MemberNotFoundException;
import com.example.gym_management_system_backend_ai.customExceptions.TrainerNotFoundException;
import com.example.gym_management_system_backend_ai.dao.MemberDao;
import com.example.gym_management_system_backend_ai.dao.TrainerDao;
import com.example.gym_management_system_backend_ai.entityDocument.Member;
import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;
import com.example.gym_management_system_backend_ai.service.TrainerService;

@Component
public class TrainerServiceImpl implements TrainerService {

	private final TrainerDao trainerDao;
	private final MemberDao memberDao;

	public TrainerServiceImpl(TrainerDao trainerDao, MemberDao memberDao) {
		this.trainerDao = trainerDao;
		this.memberDao = memberDao;
	}

	@Override
	public ResponseEntity<ResponseStructure<Trainer>> getTrainerDetails(String email) {
		Trainer trainer = trainerDao.findTrainerByEmail(email)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer not found with email: " + email));

        ResponseStructure<Trainer> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Trainer details fetched successfully");
        response.setBody(trainer);

        return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<?>> getAllRegisteredMembers(String trainerEmail, Integer page, Integer limit) {
		
		Trainer trainer = trainerDao.findTrainerByEmail(trainerEmail)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer not found with email: " + trainerEmail));

        Pageable pageable = PageRequest.of(page, limit, Sort.by("memberId").ascending());
        Page<Member> members = memberDao.findByTrainer(trainer, pageable);

        if (members.isEmpty()) {
            throw new MemberNotFoundException("No members found for given trainer details");
        }

        ResponseStructure<Page<Member>> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Fetched all registered members for trainer: " + trainer.getName());
        response.setBody(members);

        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
}
