package com.example.canvasbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "department")
    private String department;

    // Constructors, getters, and setters
}


