package com.example.assigmentthree.service.impl;

import com.example.assigmentthree.exception.ResourceNotFoundException;
import com.example.assigmentthree.model.UserData;
import com.example.assigmentthree.repository.UserRepository;
import com.example.assigmentthree.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignUpService {
    private UserRepository userRepository;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserData addNewUser(UserData userEntity) {
        if (userRepository.existsByEmail(userEntity.getEmail()))
            return null;
        return userRepository.save(userEntity);
    }

    @Override
    public UserData getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "Email", email));
    }

    @Override
    public UserData updateUser(UserData user, String email) {
        UserData currentUser = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "Id", email));

        currentUser.setFirst_name(user.getFirst_name());
        currentUser.setLast_name(user.getLast_name());
        currentUser.setEmail(user.getEmail());
        currentUser.setDate_of_birth(user.getDate_of_birth());
        currentUser.setPhone_number(user.getPhone_number());

        userRepository.save(currentUser);
        return currentUser;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "Id", id));
        userRepository.deleteById(id);
    }
}
