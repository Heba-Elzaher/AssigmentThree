package com.example.assigmentthree.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;


    @Column
    private String first_name;


    @Column
    private String last_name;


    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String date_of_birth;
 @Column
    private String phone_number;

    @Column
    private boolean isVerified;

    @Column
    private String otp;

    @Column
    private LocalDateTime otpTime;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private List<Role> roles = new ArrayList<>();
}