package com.example.assigmentthree.service;

import com.example.assigmentthree.model.UserData;

public interface LoginService {
    String authentication(UserData user);

    String verifyTwoFactor(UserData user);

    String verifyUser(String email, String otp);

    AuthResponse verifyOtp(String email);
}
