package com.example.library_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email; // Might be used for login instead of username

    @Column(unique = true)
    private String username; // Add this field if it does not exist

    private String mobile;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
