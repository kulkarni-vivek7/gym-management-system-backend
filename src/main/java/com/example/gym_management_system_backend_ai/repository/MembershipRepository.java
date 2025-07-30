package com.example.gym_management_system_backend_ai.repository;

import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.utils.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    
    Page<Membership> findByDurationAndStatus(String duration, Status status, Pageable pageable);
    
    Page<Membership> findAllByStatus(Status status, Pageable pageable);
    
    List<Membership> findAllByStatus(Status status);

	Optional<Membership> findByNameContainingIgnoreCase(String name);
}
