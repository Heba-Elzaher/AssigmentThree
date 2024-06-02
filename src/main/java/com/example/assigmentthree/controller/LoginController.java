package com.example.assigmentthree.controller;

import com.example.assigmentthree.model.AuthenticationResponse;
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

    @PostMapping(value = "/authentication")
    public ResponseEntity<String> login(@RequestBody UserData userData) {
        return new ResponseEntity<String>(loginService.authentication(userData), HttpStatus.OK);
    }

    @PostMapping(value = "/twoFA")
    public ResponseEntity<String> twoFactorAuth(@RequestBody UserData userData) {
        return new ResponseEntity<String>(loginService.generateTwoFactor(userData), HttpStatus.OK);
    }

    @GetMapping(value = "/accountVerification")
    public ResponseEntity<String> verify(@RequestParam String email, @RequestParam String otp) {
        return new ResponseEntity<>(loginService.verifyUser(email, otp), HttpStatus.OK);
    }

    @GetMapping(value = "/verifyUser")
    public ResponseEntity<AuthenticationResponse> verifyUser(@RequestParam String email) {
        return new ResponseEntity<AuthenticationResponse>(loginService.isVerified(email), HttpStatus.OK);
    }

    @PutMapping("/signOut")
    public ResponseEntity<String> signOut(@RequestBody String email) {
        return new ResponseEntity<>(loginService.signOut(email), HttpStatus.OK);
    }
}