package com.example.gym_management_system_backend_ai.daoImpl;

import com.example.gym_management_system_backend_ai.dao.TrainerDao;
import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.entityDocument.Trainer;
import com.example.gym_management_system_backend_ai.repository.TrainerRepository;
import com.example.gym_management_system_backend_ai.utils.Status;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class TrainerDaoImpl implements TrainerDao {

    @Autowired
    private TrainerRepository repository;

    @Override
    public Trainer save(Trainer trainer) {
        return repository.save(trainer);
    }

	@Override
	public List<Trainer> findAllTrainersAsceOrder() {
		// TODO Auto-generated method stub
		return repository.findAllByOrderByRegisterNoAsc();
	}

	@Override
	public void saveAllTrainers(List<Trainer> trainers) {
		// TODO Auto-generated method stub
		repository.saveAll(trainers);
	}

	@Override
	public Optional<Trainer> findTrainerByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.findByEmail(email);
	}

	@Override
	public Optional<Trainer> findById(Long trainerId) {
		// TODO Auto-generated method stub
		return repository.findByTrainerId(trainerId);
	}
	
	@Override
    public Page<Trainer> findByNameContainingIgnoreCaseAndStatus(String name, Status status, Pageable pageable) {
        return repository.findByNameContainingIgnoreCaseAndStatus(name, status, pageable);
    }

    @Override
    public Page<Trainer> findByMembershipAndStatus(Membership membership, Status status, Pageable pageable) {
        return repository.findByMembershipAndStatus(membership, status, pageable);
    }

    @Override
    public Page<Trainer> findAllByStatus(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }
    
    @Override
    public List<Trainer> findAllByMembershipAndStatus(Membership membership, Status status) {
        return repository.findAllByMembershipAndStatus(membership, status);
    }

	@Override
	public void delete(Trainer trainer) {
		// TODO Auto-generated method stub
		repository.delete(trainer);
	}
}
