package com.hotelbooking.project.controller;
import com.hotelbooking.project.dto.*;
import com.hotelbooking.project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/user/signup")
    public ResponseEntity<SignupResponse> userSignup(
            @RequestBody UserSignupRequest request) {

        authService.registerUser(request);
        return ResponseEntity.ok(new SignupResponse("User registered successfully"));
    }

    @PostMapping("/hotel/signup")
    public ResponseEntity<SignupResponse> hotelSignup(
            @RequestBody HotelSignupRequest request) {

        authService.registerHotel(request);
        return ResponseEntity.ok(new SignupResponse("Hotel registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<SignupResponse> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        authService.forgotPassword(request);

        return ResponseEntity.ok(
                new SignupResponse(
                        "Temporary password sent to your email"
                )
        );
    }

    // NEW API 2
    @PostMapping("/reset-password")
    public ResponseEntity<SignupResponse> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        authService.resetPassword(request);

        return ResponseEntity.ok(
                new SignupResponse(
                        "Password reset successfully"
                )
        );
    }

}