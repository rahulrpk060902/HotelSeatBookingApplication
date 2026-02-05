package com.hotelbooking.project.controller;

import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.dto.UserBookingResponseDTO;
import com.hotelbooking.project.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/bookings")
@RequiredArgsConstructor
public class UserBookingController {

    private final BookingService bookingService;

    @GetMapping
    public ApiResponse<List<UserBookingResponseDTO>> getMyBookings(
            Authentication authentication) {

        String userEmail = authentication.getName();
        List<UserBookingResponseDTO> bookings =
                bookingService.getUserBookings(userEmail);

        if (bookings.isEmpty()) {
            return new ApiResponse<>("No bookings found", bookings);
        }

        return new ApiResponse<>("Bookings retrieved successfully", bookings);
    }


}
