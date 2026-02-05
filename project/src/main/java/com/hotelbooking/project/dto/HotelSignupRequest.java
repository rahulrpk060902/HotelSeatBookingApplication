package com.hotelbooking.project.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelSignupRequest {
    private String hotelName;
    private String place;
    private String email;
    private String password;
    private String confirmPassword;
}