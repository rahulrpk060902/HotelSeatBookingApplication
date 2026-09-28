package com.hotelbooking.project.service;


import com.hotelbooking.project.dto.*;
import com.hotelbooking.project.entity.Hotel;
import com.hotelbooking.project.entity.User;
import com.hotelbooking.project.repository.HotelRepository;
import com.hotelbooking.project.repository.UserRepository;
import com.hotelbooking.project.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public void registerUser(UserSignupRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPlace(request.getPlace());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    @Override
    public void registerHotel(HotelSignupRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (hotelRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        if(hotelRepository.findByPhone(request.getPhone()).isPresent()){
            throw new RuntimeException("Phone number already registered");
        }

        Hotel hotel = new Hotel();
        hotel.setHotelName(request.getHotelName());
        hotel.setPlace(request.getPlace());
        hotel.setEmail(request.getEmail());
        hotel.setPassword(passwordEncoder.encode(request.getPassword()));
        hotel.setPhone(request.getPhone());

        hotelRepository.save(hotel);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        var userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent()) {

            User user = userOpt.get();

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            String token = jwtService.generateToken(user.getEmail(), user.getRole());

            return new LoginResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getRole(),
                    token,
                    "User login successful"
            );
        }

        var hotelOpt = hotelRepository.findByEmail(request.getEmail());
        if (hotelOpt.isPresent()) {

            Hotel hotel = hotelOpt.get();

            if (!passwordEncoder.matches(request.getPassword(), hotel.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            String token = jwtService.generateToken(hotel.getEmail(), hotel.getRole());

            return new LoginResponse(
                    hotel.getId(),
                    hotel.getEmail(),
                    hotel.getRole(),
                    token,
                    "Hotel login successful"
            );
        }

        throw new RuntimeException("User not found");
    }
    @Override
    public void forgotPassword(ForgotPasswordRequest request) {

        String email = request.getEmail().trim().toLowerCase();
        String role = request.getRole().trim().toUpperCase();

        if ("USER".equals(role)) {

            var userOpt = userRepository.findByEmail(email);

            if (userOpt.isEmpty()) {
                throw new RuntimeException(
                        "User email is not registered"
                );
            }

            User user = userOpt.get();

            String temporaryPassword = generateTemporaryPassword();

            user.setResetPasswordHash(
                    passwordEncoder.encode(temporaryPassword)
            );

            user.setResetPasswordExpiry(
                    LocalDateTime.now().plusMinutes(10)
            );

            userRepository.save(user);

            emailService.sendTemporaryPassword(
                    email,
                    temporaryPassword
            );

            return;
        }


        if ("HOTEL".equals(role)) {

            var hotelOpt = hotelRepository.findByEmail(email);

            if (hotelOpt.isEmpty()) {
                throw new RuntimeException(
                        "Hotel email is not registered"
                );
            }

            Hotel hotel = hotelOpt.get();

            String temporaryPassword = generateTemporaryPassword();

            hotel.setResetPasswordHash(
                    passwordEncoder.encode(temporaryPassword)
            );

            hotel.setResetPasswordExpiry(
                    LocalDateTime.now().plusMinutes(10)
            );

            hotelRepository.save(hotel);

            emailService.sendTemporaryPassword(
                    email,
                    temporaryPassword
            );

            return;
        }


        throw new RuntimeException("Invalid role");
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {

        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

            throw new RuntimeException("Passwords do not match");
        }

        String email = request.getEmail();

        // Check User
        var userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {

            User user = userOpt.get();

            if (user.getResetPasswordHash() == null) {
                throw new RuntimeException("No password reset request found");
            }

            if (user.getResetPasswordExpiry() == null ||
                    LocalDateTime.now()
                            .isAfter(user.getResetPasswordExpiry())) {

                throw new RuntimeException(
                        "Temporary password has expired"
                );
            }

            if (!passwordEncoder.matches(
                    request.getTemporaryPassword(),
                    user.getResetPasswordHash())) {

                throw new RuntimeException(
                        "Invalid temporary password"
                );
            }

            user.setPassword(
                    passwordEncoder.encode(
                            request.getNewPassword()
                    )
            );

            // Important: invalidate temporary password
            user.setResetPasswordHash(null);
            user.setResetPasswordExpiry(null);

            userRepository.save(user);

            return;
        }

        // Check Hotel
        var hotelOpt = hotelRepository.findByEmail(email);

        if (hotelOpt.isPresent()) {

            Hotel hotel = hotelOpt.get();

            if (hotel.getResetPasswordHash() == null) {
                throw new RuntimeException(
                        "No password reset request found"
                );
            }

            if (hotel.getResetPasswordExpiry() == null ||
                    LocalDateTime.now()
                            .isAfter(hotel.getResetPasswordExpiry())) {

                throw new RuntimeException(
                        "Temporary password has expired"
                );
            }

            if (!passwordEncoder.matches(
                    request.getTemporaryPassword(),
                    hotel.getResetPasswordHash())) {

                throw new RuntimeException(
                        "Invalid temporary password"
                );
            }

            hotel.setPassword(
                    passwordEncoder.encode(
                            request.getNewPassword()
                    )
            );

            hotel.setResetPasswordHash(null);
            hotel.setResetPasswordExpiry(null);

            hotelRepository.save(hotel);

            return;
        }

        throw new RuntimeException("User not found");
    }

    private String generateTemporaryPassword() {

        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                        "abcdefghijklmnopqrstuvwxyz" +
                        "0123456789";

        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

}
