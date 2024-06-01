package com.example.assigmentthree.service;


import com.example.assigmentthree.model.UserData;

public interface SignUpService {
    UserData addNewUser(UserData userEntity);

    UserData getUserByEmail(String email);

    UserData updateUser(UserData userEntity, String email);

    void deleteUser(long id);
}