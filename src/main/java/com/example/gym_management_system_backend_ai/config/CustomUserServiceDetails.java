package com.example.gym_management_system_backend_ai.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.gym_management_system_backend_ai.customExceptions.InvalidLoginCredentialsException;
import com.example.gym_management_system_backend_ai.dao.LoginDao;
import com.example.gym_management_system_backend_ai.entityDocument.LoginDetails;

@Component
public class CustomUserServiceDetails implements UserDetailsService 
{
	
	private final LoginDao loginDao;

	public CustomUserServiceDetails(LoginDao loginDao) {
		super();
		this.loginDao = loginDao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		LoginDetails loginDetails = loginDao.findByEmail(email).orElseThrow(() -> new 
				InvalidLoginCredentialsException("Invalid Email Id... Unable to Find Login Details"));
		
		return User
                .withUsername(loginDetails.getEmail())
                .password(loginDetails.getOtp())
                .roles("USER")
                .build();
	}

}
