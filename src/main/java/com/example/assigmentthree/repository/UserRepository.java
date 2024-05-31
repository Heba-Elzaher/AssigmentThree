package com.example.assigmentthree.repository;

import com.example.assigmentthree.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByEmail(String email);
    Boolean existsByEmail(String email);
}