package com.example.gym_management_system_backend_ai.daoImpl;

import com.example.gym_management_system_backend_ai.dao.LoginDao;
import com.example.gym_management_system_backend_ai.entityDocument.LoginDetails;
import com.example.gym_management_system_backend_ai.entityDocument.Membership;
import com.example.gym_management_system_backend_ai.repository.LoginMongoRepository;
import com.example.gym_management_system_backend_ai.utils.Roles;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginDaoImpl implements LoginDao {

    private final LoginMongoRepository loginRepo;

    public LoginDaoImpl(LoginMongoRepository loginRepo) {
		this.loginRepo = loginRepo;
	}

	@Override
    public Optional<LoginDetails> findByEmail(String email) {
        return loginRepo.findFirstByEmail(email);
    }

    @Override
    public LoginDetails save(LoginDetails loginDetails) {
        return loginRepo.save(loginDetails);
    }

    @Override
    public boolean existsByRoleAdmin() {
        return loginRepo.existsByRole(Roles.ADMIN);
    }

	@Override
	public void updateLoginDetails(LoginDetails loginDetails) {
		// TODO Auto-generated method stub
		loginRepo.save(loginDetails);
	}

	@Override
	public Optional<Membership> findByPhno(Long phno) {
		// TODO Auto-generated method stub
		return loginRepo.findFirstByPhno(phno);
	}

	@Override
	public void delete(LoginDetails loginDetails) {
		// TODO Auto-generated method stub
		loginRepo.delete(loginDetails);
	}
}
