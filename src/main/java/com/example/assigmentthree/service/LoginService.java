package com.example.assigmentthree.service;

import com.example.assigmentthree.model.UserData;
import com.example.assigmentthree.model.AuthResponse;

public interface LoginService {
    String authentication(UserData user);

    String generateTwoFactor(UserData user);

    String verifyUser(String email, String otp);

    AuthResponse isVerified(String email);
}
