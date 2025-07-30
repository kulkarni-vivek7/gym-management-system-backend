package com.example.gym_management_system_backend_ai.repository;

import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.utils.Status;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

	Optional<Trainer> findByEmail(String email);

	Optional<Trainer> findByTrainerId(Long trainerId);
	
	Page<Trainer> findByNameContainingIgnoreCaseAndStatus(String name, Status status, Pageable pageable);
	
    Page<Trainer> findByMembershipAndStatus(Membership membership, Status status, Pageable pageable);
    
    Page<Trainer> findAllByStatus(Status status, Pageable pageable);
    
    List<Trainer> findAllByMembershipAndStatus(Membership membership, Status status);

    List<Trainer> findAllByOrderByRegisterNoAsc();
}
