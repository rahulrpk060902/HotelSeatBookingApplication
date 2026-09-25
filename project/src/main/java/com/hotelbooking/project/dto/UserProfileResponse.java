package com.hotelbooking.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserProfileResponse {

    private UUID id;
    private String name;
    private String phone;
    private String email;
    private String place;
}

