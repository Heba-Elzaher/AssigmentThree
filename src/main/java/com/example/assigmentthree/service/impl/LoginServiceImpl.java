package com.example.assigmentthree.service.impl;

import com.example.assigmentthree.twoFA.EmailTemplate;
import com.example.assigmentthree.twoFA.OtpGenerator;
import jakarta.mail.MessagingException;

import com.example.assigmentthree.exception.ResourceNotFoundException;
import com.example.assigmentthree.model.AuthenticationResponse;
import com.example.assigmentthree.model.UserData;
import com.example.assigmentthree.repository.UserRepository;
import com.example.assigmentthree.jwt.GenerateJWT;
import com.example.assigmentthree.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class LoginServiceImpl implements LoginService {
    private AuthenticationManager authenticationManager;
    private GenerateJWT generateJWT;
    private OtpGenerator otpGenerator;
    private EmailTemplate emailTemplate;
    private UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, GenerateJWT generateJWT, OtpGenerator otpGenerator, EmailTemplate emailTemplate, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.generateJWT = generateJWT;
        this.otpGenerator = otpGenerator;
        this.emailTemplate = emailTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public String authentication(UserData userData) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(userData.getEmail(), userData.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "Authenticated";
    }

    @Override
    public String generateTwoFactor(UserData user) {
        try {
            String otp = otpGenerator.generateOTP(6);
            emailTemplate.sendOtpEmail(user.getEmail(), otp);
            UserData userData = userRepository.findByEmail(user.getEmail()).orElseThrow(
                    () -> new ResourceNotFoundException("User", "Email", user.getEmail()));
            userData.setOtp(otp);
            userData.setOtpTime(LocalDateTime.now());
            userRepository.save(userData);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send the OTP. Please try again.");
        }
        return "OTP has been generated ";
    }

    @Override
    public String verifyUser(String email, String otp) {
        UserData user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No user found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpTime(),
                LocalDateTime.now()).getSeconds() < (60)) {
            user.setVerified(true);
            userRepository.save(user);
            return "Successfully authenticated!";
        }
        return "The OTP has expired or incorrect. ";
    }

    @Override
    public AuthenticationResponse isVerified(String email) {
        UserData user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No user found with this email: " + email));
        if (user.isVerified()) {
            String token = generateJWT.tokenGenerator(email); // generating the token and passing the email
            return new AuthenticationResponse(token);
        }
        return null;
    }

    @Override
    public String signOut(String email) {
        System.out.println(email);
        UserData user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        user.setVerified(false);
        System.out.println("TEST");
        user.setOtp(null);
        user.setOtpTime(null);
        userRepository.save(user);
        return "log out";
    }

}
