package com.example.gym_management_system_backend_ai.daoImpl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.gym_management_system_backend_ai.dao.AdminDao;
import com.example.gym_management_system_backend_ai.entityDocument.User;
import com.example.gym_management_system_backend_ai.repository.AdminRepository;

@Component
public class AdminDaoImpl implements AdminDao {
	
	private final AdminRepository adminRepository;

	public AdminDaoImpl(AdminRepository adminRepository) {
		super();
		this.adminRepository = adminRepository;
	}

	@Override
	public User saveAdmin(User user) {
		// TODO Auto-generated method stub
		return adminRepository.save(user);
	}
	
	@Override
    public Optional<User> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

	@Override
	public User updateAdmin(User user) {
		// TODO Auto-generated method stub
		return adminRepository.save(user);
	}

}
