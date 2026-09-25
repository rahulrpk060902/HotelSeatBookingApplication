package com.hotelbooking.project.service;

import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.dto.HotelProfileDto;
import com.hotelbooking.project.dto.HotelUpdateRequest;
import com.hotelbooking.project.entity.Hotel;
import com.hotelbooking.project.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HotelProfileServiceImpl implements HotelProfileService {

    private final HotelRepository hotelRepository;

    public HotelProfileServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public ApiResponse<HotelProfileDto> getHotelProfile(UUID hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        HotelProfileDto profileDto = new HotelProfileDto(
                hotel.getId(),
                hotel.getHotelName(),
                hotel.getPlace(),
                hotel.getEmail(),
                hotel.getPhone()
        );

        return new ApiResponse<>(
                "Hotel profile loaded successfully",
                profileDto
        );
    }

    @Override
    public ApiResponse<HotelUpdateRequest> updateHotelProfile(UUID hotelId, HotelUpdateRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Check if email/phone is used by another hotel
        if (!hotel.getEmail().equals(request.getEmail()) &&
                hotelRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        if (!hotel.getPhone().equals(request.getPhone()) &&
                hotelRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new RuntimeException("Phone number already registered");
        }

        // Update fields
        hotel.setHotelName(request.getHotelName());
        hotel.setPlace(request.getPlace());
        hotel.setEmail(request.getEmail());
        hotel.setPhone(request.getPhone());

        hotelRepository.save(hotel);

        // Prepare response DTO
        HotelUpdateRequest response = new HotelUpdateRequest();
        response.setHotelName(hotel.getHotelName());
        response.setPlace(hotel.getPlace());
        response.setEmail(hotel.getEmail());
        response.setPhone(hotel.getPhone());

        return new ApiResponse<>("Hotel profile updated successfully", response);
    }

}

