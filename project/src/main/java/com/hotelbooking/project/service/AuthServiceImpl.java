package com.hotelbooking.project.service;


import com.hotelbooking.project.dto.HotelSignupRequest;
import com.hotelbooking.project.dto.LoginRequest;
import com.hotelbooking.project.dto.LoginResponse;
import com.hotelbooking.project.dto.UserSignupRequest;
import com.hotelbooking.project.entity.Hotel;
import com.hotelbooking.project.entity.User;
import com.hotelbooking.project.repository.HotelRepository;
import com.hotelbooking.project.repository.UserRepository;
import com.hotelbooking.project.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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

}
