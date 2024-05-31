package com.example.assigmentthree.model;

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
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] photo;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private long user_id;
}