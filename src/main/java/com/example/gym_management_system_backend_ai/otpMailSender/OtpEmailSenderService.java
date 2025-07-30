package com.example.gym_management_system_backend_ai.otpMailSender;

import java.security.SecureRandom;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class OtpEmailSenderService {

	private final JavaMailSender mailSender;

	public OtpEmailSenderService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	private static final String SUBJECT = "The Blaze Gym - "
			+ "Your One Time Password (OTP)";
	
	public String generateOtp()
	{
		SecureRandom random = new SecureRandom();
		
		int otp = 100000 + random.nextInt(900000);
		
		return String.valueOf(otp);
	}
	
//	Sending OTP to the given email id with well-structured HTML mail Template.
	
	public void sendOtpEmail(String toEmail, String otp) throws MessagingException
	{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setTo(toEmail);
		helper.setSubject(SUBJECT);
		helper.setText(buildEmailHtml(otp), true);
		
		mailSender.send(message);
	}
	
//	Build the HTML template for the OTP email
	
	private String buildEmailHtml(String otp)
	{
		return "<!DOCTYPE html>" +
		        "<html>" +
		        "<head>" +
		        "    <meta charset='UTF-8'>" +
		        "    <meta name='viewport' content='width=device-width, initial-scale=1'>" +
		        "    <title>OTP Verification</title>" +
		        "    <style>" +
		        "        body { font-family: Arial, sans-serif; background-color: #f8f9fa; margin: 0; padding: 0; text-align: center; }" +
		        "        .container { max-width: 480px; margin: 40px auto; background: white; padding: 25px; border-radius: 10px; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); }" +
		        "        .header { font-size: 22px; font-weight: bold; color: #333; }" +
		        "        .otp { font-size: 28px; font-weight: bold; color: #007bff; background: #eef2ff; padding: 10px; display: inline-block; border-radius: 5px; margin: 20px 0; }" +
		        "        .message { font-size: 16px; color: #555; }" +
		        "        .btn { display: inline-block; padding: 12px 20px; background: #007bff; color: white; text-decoration: none; border-radius: 5px; font-size: 16px; font-weight: bold; margin-top: 20px; }" +
		        "        .footer { margin-top: 20px; font-size: 13px; color: #777; }" +
		        "    </style>" +
		        "</head>" +
		        "<body>" +
		        "    <div class='container'>" +
		        "        <div class='header'>🔐 OTP Verification</div>" +
		        "        <p class='message'>Use the following One-Time Password (OTP) to proceed with your action:</p>" +
		        "        <div class='otp'>" + otp + "</div>" +
		        "        <p class='message'>This OTP is valid for only 10 minutes. Do not share it with anyone for security reasons.</p>" +
		        "        <p class='footer'>If you did not request this, please ignore this email or contact our support team.</p>" +
		        "    </div>" +
		        "</body>" +
		        "</html>";

	}
}
