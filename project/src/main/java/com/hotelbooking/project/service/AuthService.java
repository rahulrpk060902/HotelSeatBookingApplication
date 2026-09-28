package com.hotelbooking.project.service;


import com.hotelbooking.project.dto.*;

public interface AuthService {
    void registerUser(UserSignupRequest request);
    void registerHotel(HotelSignupRequest request);
    public LoginResponse login(LoginRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}