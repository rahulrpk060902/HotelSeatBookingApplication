package com.hotelbooking.project.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {
    private String name;
    private String phone;
    private String email;
    private String place;
    private String password;
    private String confirmPassword;
}
