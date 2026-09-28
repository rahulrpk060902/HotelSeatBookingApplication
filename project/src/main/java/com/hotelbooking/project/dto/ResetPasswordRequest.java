package com.hotelbooking.project.dto;


import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String email;

    private String temporaryPassword;

    private String newPassword;

    private String confirmPassword;
}