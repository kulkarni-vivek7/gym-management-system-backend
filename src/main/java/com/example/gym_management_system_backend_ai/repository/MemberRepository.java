package com.example.gym_management_system_backend_ai.repository;

import com.example.gym_management_system_backend_ai.entityDocument.Member;
import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.utils.Status;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);
	
	Page<Member> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
    Page<Member> findByTrainer(Trainer trainer, Pageable pageable);
    
    Page<Member> findByMembership(Membership membership, Pageable pageable);
    
    Page<Member> findAllByStatus(Status status, Pageable pageable);

	Optional<Member> findByMemberId(Long memberId);

	List<Member> findByTrainer_RegisterNo(Long registerNo);

	List<Member> findByMembershipId(Long id);

	List<Member> findAllByOrderByRegisterNoAsc();
}
