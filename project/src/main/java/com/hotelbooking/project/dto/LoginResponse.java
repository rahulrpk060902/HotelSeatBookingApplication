package com.hotelbooking.project.dto;


import com.hotelbooking.project.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private UUID id;
    private String email;
    private Role role;
    private String token;
    private String message;
}
