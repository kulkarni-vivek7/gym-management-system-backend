package com.example.gym_management_system_backend_ai.dao;

import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.utils.Status;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipDao {
	
    Optional<Membership> findByNameContainingIgnoreCase(String name);
    
    Membership save(Membership membership);
    
    Page<Membership> findByDurationAndStatus(String duration, Status status, Pageable pageable);
    
    Page<Membership> findAllByStatus(Status status, Pageable pageable);
    
    List<Membership> findAllByStatus(Status status);

	void delete(Membership membership);
}
