package com.example.assigmentthree.model;

import com.example.assigmentthree.spring_security.EncryptionDecryption;
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
    @Convert(converter = EncryptionDecryption.class)
    private long user_id;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private String first_name;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private String last_name;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private String email;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private String password;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private String date_of_birth;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private boolean isVerified;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private String otp;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private LocalDateTime otpTime;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private List<Role> roles = new ArrayList<>();
}