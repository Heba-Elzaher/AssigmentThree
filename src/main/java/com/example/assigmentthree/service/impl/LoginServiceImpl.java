//package com.example.assigmentthree.service.impl;
//
//import jakarta.mail.MessagingException;
//
//import com.example.assigmentthree.exception.ResourceNotFoundException;
//import com.example.assigmentthree.model.AuthResponse;
//import com.example.assigmentthree.model.UserData;
//import com.example.assigmentthree.repository.UserRepository;
//import com.example.assigmentthree.security.JwtGenerator;
//import com.example.assigmentthree.service.LoginService;
//import com.example.assigmentthree.util.EmailUtil;
//import com.example.assigmentthree.util.OtpUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//
//@Service
//public class LoginServiceImpl implements LoginService {
//    private AuthenticationManager authenticationManager;
//    /* private JwtGenerator jwtGenerator;
//    private OtpUtil otpUtil;
//    private EmailUtil emailUtil;*/
//    private UserRepository userRepository;
//
//    @Autowired
//    public LoginServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository) {
//        this.authenticationManager = authenticationManager;
//
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public String authentication(UserData userData) {
//        Authentication authentication = authenticationManager.
//                authenticate(new UsernamePasswordAuthenticationToken(userData.getEmail()
//                        , userData.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return "This user already exists.";
//    }
//
//    @Override
//    public String verifyTwoFactor(UserData user) {
//        // String otp = otpUtil.generateOtp();
////        try {
////            emailUtil.sendOtpEmail(user.getEmail(), otp);
////        } catch (MessagingException e) {
////            throw new RuntimeException("Failed to send the OTP. Please try again.");
////        }
//        UserData userData = userRepository.findByEmail(user.getEmail()).orElseThrow(
//                () -> new ResourceNotFoundException("User", "Email", user.getEmail()));
////        userData.setOtp(otp);
////        userData.setOtpGeneratedTime(LocalDateTime.now());
//        userRepository.save(userData);
//
//        return "OTP has been generated and sent to " + user.getEmail();
//    }
//
//    @Override
//    public String verifyUser(String email, String otp) {
//        UserData user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("No user found with this email: " + email));
//        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
//                LocalDateTime.now()).getSeconds() < (60)) {
//            user.setActive(true);
//            userRepository.save(user);
//            return "You have been successfully authenticated. Please return to the website and click \"CONTINUE\".";
//        }
//        return "The OTP has expired. Please try again.  ";
//    }
//
//    @Override
//    public AuthResponse verifyOtp(String email) {
//        UserData user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("No user found with this email: " + email));
//        boolean isActive = user.isActive();
//        if (isActive) {
//            String token = jwtGenerator.generateToken(email);
//            return new AuthResponse(token);
//        }
//        return null;
//    }
//}
