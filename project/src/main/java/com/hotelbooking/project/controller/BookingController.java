package com.hotelbooking.project.controller;

import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.dto.UserBookingResponseDTO;
import com.hotelbooking.project.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/schedule/{scheduleId}")
    public ApiResponse<Void> bookSeat(
            Authentication authentication,
            @PathVariable UUID scheduleId) {

        bookingService.bookSeat(authentication.getName(), scheduleId);
        return new ApiResponse<>("Seat booked successfully", null);
    }

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

