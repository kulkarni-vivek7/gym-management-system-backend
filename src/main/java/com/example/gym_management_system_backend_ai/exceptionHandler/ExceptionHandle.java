package com.example.gym_management_system_backend_ai.exceptionHandler;

import com.example.gym_management_system_backend_ai.customExceptions.AdminAlreadyExistsException;
import com.example.gym_management_system_backend_ai.customExceptions.EmailAlreadyExistsException;
import com.example.gym_management_system_backend_ai.customExceptions.InactiveAccountException;
import com.example.gym_management_system_backend_ai.customExceptions.InvalidOtpException;
import com.example.gym_management_system_backend_ai.customExceptions.LoginDetailsNotFoundException;
import com.example.gym_management_system_backend_ai.customExceptions.MemberNotFoundException;
import com.example.gym_management_system_backend_ai.customExceptions.MembershipAlreadyExistsException;
import com.example.gym_management_system_backend_ai.customExceptions.MembershipNotFoundException;
import com.example.gym_management_system_backend_ai.customExceptions.OtpAlreadyUsedException;
import com.example.gym_management_system_backend_ai.customExceptions.OtpExpiredException;
import com.example.gym_management_system_backend_ai.customExceptions.PhoneAlreadyExistsException;
import com.example.gym_management_system_backend_ai.customExceptions.TrainerNotFoundException;
import com.example.gym_management_system_backend_ai.customExceptions.UpdateTrainerException;
import com.example.gym_management_system_backend_ai.customExceptions.UserNotFoundException;
import com.example.gym_management_system_backend_ai.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAdminExists(AdminAlreadyExistsException e) {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.CONFLICT.value(), e.getMessage()),
                HttpStatus.CONFLICT
        );
    }
    
    @ExceptionHandler(MembershipAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleMembershipExists(MembershipAlreadyExistsException e) {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.CONFLICT.value(), e.getMessage()),
                HttpStatus.CONFLICT
        );
    }
    
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleEmailExists(EmailAlreadyExistsException e) {
    	return new ResponseEntity<>(
    			new ExceptionResponse(HttpStatus.CONFLICT.value(), e.getMessage()),
    			HttpStatus.CONFLICT
    			);
    }
    
    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handlePhoneNumberExists(PhoneAlreadyExistsException e) {
    	return new ResponseEntity<>(
    			new ExceptionResponse(HttpStatus.CONFLICT.value(), e.getMessage()),
    			HttpStatus.CONFLICT
    			);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFound(UserNotFoundException e) {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
    
    @ExceptionHandler(MembershipNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleMembershipNotFound(MembershipNotFoundException e) {
    	return new ResponseEntity<>(
    			new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
    			HttpStatus.NOT_FOUND
    			);
    }
    
    @ExceptionHandler(TrainerNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTrainerNotFound(TrainerNotFoundException e) {
    	return new ResponseEntity<>(
    			new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
    			HttpStatus.NOT_FOUND
    			);
    }
    
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleMemberNotFound(MemberNotFoundException e) {
    	return new ResponseEntity<>(
    			new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
    			HttpStatus.NOT_FOUND
    			);
    }
    
    @ExceptionHandler(LoginDetailsNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleLoginDetailsNotFound(LoginDetailsNotFoundException e) {
    	return new ResponseEntity<>(
    			new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
    			HttpStatus.NOT_FOUND
    			);
    }
    
    @ExceptionHandler(UpdateTrainerException.class)
    public ResponseEntity<ExceptionResponse> handleLoginDetailsNotFound(UpdateTrainerException e) {
    	return new ResponseEntity<>(
    			new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
    			HttpStatus.BAD_REQUEST
    			);
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidOtp(InvalidOtpException e) {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(InactiveAccountException.class)
    public ResponseEntity<ExceptionResponse> handleInactive(InactiveAccountException e) {
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({OtpAlreadyUsedException.class, OtpExpiredException.class})
    public ResponseEntity<ExceptionResponse> handleOtpIssues(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneral(Exception e) {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error: " + e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
