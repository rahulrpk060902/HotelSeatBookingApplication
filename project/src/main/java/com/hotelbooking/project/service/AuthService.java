package com.hotelbooking.project.service;


import com.hotelbooking.project.dto.HotelSignupRequest;
import com.hotelbooking.project.dto.LoginRequest;
import com.hotelbooking.project.dto.LoginResponse;
import com.hotelbooking.project.dto.UserSignupRequest;

public interface AuthService {
    void registerUser(UserSignupRequest request);
    void registerHotel(HotelSignupRequest request);
    public LoginResponse login(LoginRequest request);

}