package com.example.gym_management_system_backend_ai.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.utils.Status;

@Repository
public interface TrainerDao {
    Trainer save(Trainer trainer);

	List<Trainer> findAllTrainersAsceOrder();

	void saveAllTrainers(List<Trainer> trainers);

	Optional<Trainer> findTrainerByEmail(String email);

	Optional<Trainer> findById(Long trainerId);
	
	Page<Trainer> findByNameContainingIgnoreCaseAndStatus(String name, Status status, Pageable pageable);
	
    Page<Trainer> findByMembershipAndStatus(Membership membership, Status status, Pageable pageable);
    
    Page<Trainer> findAllByStatus(Status status, Pageable pageable);
    
    List<Trainer> findAllByMembershipAndStatus(Membership membership, Status status);

	void delete(Trainer trainer);
}
