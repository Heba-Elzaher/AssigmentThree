package com.example.assigmentthree.controller;

import com.example.assigmentthree.model.AuthResponse;
import com.example.assigmentthree.model.UserData;
import com.example.assigmentthree.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        super();
        this.loginService = loginService;
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<String> login(@RequestBody UserData userData) {
        return new ResponseEntity<String>(loginService.authentication(userData), HttpStatus.OK);
    }

    @PostMapping(value = "/mfa")
    public ResponseEntity<String> twoFactorAuth(@RequestBody UserData userData) {
        return new ResponseEntity<String>(loginService.toString(userData), HttpStatus.OK);
    }

    @GetMapping(value = "/verify-account")
    public ResponseEntity<String> verify(@RequestParam String email, @RequestParam String otp) {
        return new ResponseEntity<>(loginService.verifyUser(email, otp), HttpStatus.OK);
    }

    @GetMapping(value = "/verify")
    public ResponseEntity<AuthResponse> verify(@RequestParam String email) {
        return new ResponseEntity<AuthResponse>(loginService.verifyOtp(email), HttpStatus.OK);
    }
}