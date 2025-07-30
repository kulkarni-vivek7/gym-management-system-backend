package com.example.gym_management_system_backend_ai.serviceImpl;

import com.example.gym_management_system_backend_ai.config.JwtUtil;
import com.example.gym_management_system_backend_ai.customExceptions.AdminAlreadyExistsException;
import com.example.gym_management_system_backend_ai.customExceptions.InactiveAccountException;
import com.example.gym_management_system_backend_ai.customExceptions.InvalidOtpException;
import com.example.gym_management_system_backend_ai.customExceptions.OtpAlreadyUsedException;
import com.example.gym_management_system_backend_ai.customExceptions.OtpExpiredException;
import com.example.gym_management_system_backend_ai.customExceptions.UserNotFoundException;
import com.example.gym_management_system_backend_ai.dao.AdminDao;
import com.example.gym_management_system_backend_ai.dao.LoginDao;
import com.example.gym_management_system_backend_ai.dto.AdminRegisterDto;
import com.example.gym_management_system_backend_ai.dto.LoginRequestDto;
import com.example.gym_management_system_backend_ai.entityDocument.LoginDetails;
import com.example.gym_management_system_backend_ai.entityDocument.User;
import com.example.gym_management_system_backend_ai.otpMailSender.OtpEmailSenderService;
import com.example.gym_management_system_backend_ai.response.ResponseStructure;
import com.example.gym_management_system_backend_ai.service.LoginService;
import com.example.gym_management_system_backend_ai.utils.Roles;
import com.example.gym_management_system_backend_ai.utils.Status;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class LoginServiceImpl implements LoginService {

    private final LoginDao loginDao;
    
    private final AdminDao adminDao;
    
    private final JwtUtil jwtUtil;

    private final OtpEmailSenderService otpService;
    
    private static final Long OTP_VALID_DURATION = 10L;

    public LoginServiceImpl(LoginDao loginDao, AdminDao adminDao, OtpEmailSenderService otpService, JwtUtil jwtUtil) {
		super();
		this.loginDao = loginDao;
		this.adminDao = adminDao;
		this.jwtUtil = jwtUtil;
		this.otpService = otpService;
	}

	@Override
    public ResponseEntity<ResponseStructure<User>> adminRegister(AdminRegisterDto dto) {
        if (loginDao.existsByRoleAdmin()) {
            throw new AdminAlreadyExistsException("Cannot register more than one Admin.");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setGender(dto.getGender());
        user.setPhno(dto.getPhno());
        user.setEmail(dto.getEmail());
        user.setStatus(Status.ACTIVE);
        
        user = adminDao.saveAdmin(user);

        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setEmail(dto.getEmail());
        loginDetails.setPhno(dto.getPhno());
        loginDetails.setRole(Roles.ADMIN);
        loginDetails.setStatus(Status.ACTIVE);
        loginDetails.setTimeStamp(LocalDateTime.now());
        loginDetails.setIsLoggedIn(false);

        loginDetails = loginDao.save(loginDetails);

        ResponseStructure<User> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Admin Registered Successfully");
        response.setBody(user);

        return new ResponseEntity<ResponseStructure<User>>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<String>> sendOtp(String email) {
        LoginDetails loginDetails = loginDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user found for email: " + email + ". Please Register First"));

        // ✅ Check if account is INACTIVE
        if (loginDetails.getStatus() == Status.INACTIVE) {
            throw new InactiveAccountException("Inactive Account!... Unable to Send OTP Email");
        }

        String otp = otpService.generateOtp();
        
        loginDetails.setOtp(otp);
        loginDetails.setTimeStamp(LocalDateTime.now());
        loginDetails.setIsLoggedIn(false);
        loginDao.updateLoginDetails(loginDetails);

        try {
            otpService.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email");
        }

        ResponseStructure<String> response = new ResponseStructure<>(
                HttpStatus.OK.value(),
                "OTP sent successfully",
                "OTP has been sent to your email"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ResponseStructure<String>> loginWithOtp(LoginRequestDto request) {
        String email = request.getEmail();
        String inputOtp = request.getOtp();

        LoginDetails loginDetails = loginDao.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User with email not found: " + email));

        if (loginDetails.getStatus() == Status.INACTIVE) {
            throw new InactiveAccountException("Inactive Account!... Unable to Login");
        }

        if (!inputOtp.equals(loginDetails.getOtp())) {
            throw new InvalidOtpException("Invalid OTP... Unable to Login");
        }

        if (Boolean.TRUE.equals(loginDetails.getIsLoggedIn())) {
            throw new OtpAlreadyUsedException("OTP Already Used... Generate a new OTP For Login");
        }

        long minutesPassed = ChronoUnit.MINUTES.between(loginDetails.getTimeStamp(), LocalDateTime.now());
        if (minutesPassed > OTP_VALID_DURATION) {
            throw new OtpExpiredException("OTP Expired... Generate a New OTP");
        }

        String token = jwtUtil.generateToken(email);
        loginDetails.setIsLoggedIn(true);
        loginDao.updateLoginDetails(loginDetails);

        String message = loginDetails.getRole() + " Logged In Successfully";
        ResponseStructure<String> response = new ResponseStructure<>(
                HttpStatus.OK.value(), message, token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
