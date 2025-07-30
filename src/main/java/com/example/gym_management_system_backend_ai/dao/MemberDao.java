package com.example.gym_management_system_backend_ai.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.gym_management_system_backend_ai.entityDocument.Member;
import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.utils.Status;

@Repository
public interface MemberDao {
	
    Member save(Member member);

	List<Member> findAllMembersAsceOrder();

	void saveAllMembers(List<Member> members);

	Optional<Member> findMemberByEmail(String email);
	
	Page<Member> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
    Page<Member> findByTrainer(Trainer trainer, Pageable pageable);
    
    Page<Member> findByMembership(Membership membership, Pageable pageable);
    
    Page<Member> findAllByStatus(Status status, Pageable pageable);

	Optional<Member> findByMemberId(Long valueOf);

	List<Member> findByTrainer_RegisterNo(Long registerNo);

	List<Member> findByMembershipId(Long id);

	void delete(Member member);
}
