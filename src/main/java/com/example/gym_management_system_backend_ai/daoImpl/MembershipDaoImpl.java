package com.example.gym_management_system_backend_ai.daoImpl;

import com.example.gym_management_system_backend_ai.dao.MembershipDao;
import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.repository.MembershipRepository;
import com.example.gym_management_system_backend_ai.utils.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MembershipDaoImpl implements MembershipDao {

    @Autowired
    private MembershipRepository repository;

    @Override
    public Optional<Membership> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Membership save(Membership membership) {
        return repository.save(membership);
    }
    
    @Override
    public Page<Membership> findByDurationAndStatus(String duration, Status status, Pageable pageable) {
        return repository.findByDurationAndStatus(duration, status, pageable);
    }

    @Override
    public Page<Membership> findAllByStatus(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }
    
    @Override
    public List<Membership> findAllByStatus(Status status) {
        return repository.findAllByStatus(status);
    }

	@Override
	public void delete(Membership membership) {
		// TODO Auto-generated method stub
		repository.delete(membership);
	}
}
