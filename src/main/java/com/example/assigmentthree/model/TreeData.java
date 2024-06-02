package com.example.assigmentthree.model;

import com.example.assigmentthree.spring_security.EncryptionDecryption;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "trees")
public class TreeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tree_id;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @Convert(converter = EncryptionDecryption.class)
    private byte[] photo;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private String location;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private int points;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private String date;

    @Convert(converter = EncryptionDecryption.class)
    @Column
    private String species;

    @Convert(converter = EncryptionDecryption.class)
    @Column(columnDefinition = "TEXT")
    private String endangerment;

    @Convert(converter = EncryptionDecryption.class)
    @Column( columnDefinition = "TEXT")
    private String tips;

    @Convert(converter = EncryptionDecryption.class)
    @Column( columnDefinition = "TEXT")
    private String rarity;

    @Column
    @Convert(converter = EncryptionDecryption.class)
    private long user_id;
}