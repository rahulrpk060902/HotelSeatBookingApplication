package com.hotelbooking.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpdateRequest {

    private String name;
    private String phone;
    private String place;
}
