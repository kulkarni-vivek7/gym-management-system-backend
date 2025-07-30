package com.example.gym_management_system_backend_ai.daoImpl;

import com.example.gym_management_system_backend_ai.dao.MemberDao;
import com.example.gym_management_system_backend_ai.entityDocument.Member;
import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.repository.MemberRepository;
import com.example.gym_management_system_backend_ai.utils.Status;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private MemberRepository repository;

    @Override
    public Member save(Member member) {
        return repository.save(member);
    }

	@Override
	public List<Member> findAllMembersAsceOrder() {
		// TODO Auto-generated method stub
		return repository.findAllByOrderByRegisterNoAsc();
	}

	@Override
	public void saveAllMembers(List<Member> members) {
		// TODO Auto-generated method stub
		repository.saveAll(members);
	}

	@Override
	public Optional<Member> findMemberByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.findByEmail(email);
	}
	
	@Override
    public Page<Member> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<Member> findByTrainer(Trainer trainer, Pageable pageable) {
        return repository.findByTrainer(trainer, pageable);
    }

    @Override
    public Page<Member> findByMembership(Membership membership, Pageable pageable) {
        return repository.findByMembership(membership, pageable);
    }

    @Override
    public Page<Member> findAllByStatus(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

	@Override
	public Optional<Member> findByMemberId(Long memberId) {
		// TODO Auto-generated method stub
		return repository.findByMemberId(memberId);
	}

	@Override
	public List<Member> findByTrainer_RegisterNo(Long registerNo) {
		// TODO Auto-generated method stub
		return repository.findByTrainer_RegisterNo(registerNo);
	}

	@Override
	public List<Member> findByMembershipId(Long id) {
		// TODO Auto-generated method stub
		return repository.findByMembershipId(id);
	}

	@Override
	public void delete(Member member) {
		// TODO Auto-generated method stub
		repository.delete(member);
	}
}
