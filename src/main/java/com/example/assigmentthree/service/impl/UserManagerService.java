package com.example.assigmentthree.service.impl;

import com.example.assigmentthree.model.Role;
import com.example.assigmentthree.model.UserData;
import com.example.assigmentthree.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// authentication manager uses this class to check if the credentials are in the user service (DB)
// creates user object if the user is authorized
@Service
public class UserManagerService implements UserDetailsService {
    private UserRepository userRepository;
    @Autowired
    public UserManagerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserData user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("username not found")); // inside the DB
        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthority(user.getRoles()));
    }
    private Collection<GrantedAuthority> mapRolesToAuthority(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}
