package com.hotelbooking.project.controller;

import com.hotelbooking.project.dto.HotelBookingResponseDTO;
import com.hotelbooking.project.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel/bookings")
@RequiredArgsConstructor
public class HotelBookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<HotelBookingResponseDTO>> getHotelBookings(
            Authentication authentication) {

        String hotelEmail = authentication.getName();
        return ResponseEntity.ok(
                bookingService.getHotelBookings(hotelEmail)
        );
    }
}
