package com.example.assigmentthree.service.impl;

import com.example.assigmentthree.exception.ResourceNotFoundException;
import com.example.assigmentthree.model.Role;
import com.example.assigmentthree.model.UserData;
import com.example.assigmentthree.repository.RoleRepository;
import com.example.assigmentthree.repository.UserRepository;
import com.example.assigmentthree.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SignUpServiceImpl implements SignUpService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserData addNewUser(UserData userData) {
        if (userRepository.existsByEmail(userData.getEmail()))
            return null;

        String password = userData.getPassword();
        userData.setPassword(passwordEncoder.encode(password));
        Role role = roleRepository.findByRole("USER").get();
        userData.setRoles(Collections.singletonList(role));
        return userRepository.save(userData);
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
