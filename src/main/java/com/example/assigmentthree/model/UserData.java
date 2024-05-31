package com.example.assigmentthree.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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

}