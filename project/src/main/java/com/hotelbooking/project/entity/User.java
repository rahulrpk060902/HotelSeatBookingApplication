package com.hotelbooking.project.entity;

import com.hotelbooking.project.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String phone;

    @Column(unique = true)
    private String email;

    private String place;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
}
