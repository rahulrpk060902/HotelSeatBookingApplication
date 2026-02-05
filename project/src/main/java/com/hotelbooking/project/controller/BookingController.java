package com.hotelbooking.project.controller;

import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/{seatId}")
    public ApiResponse<Void> bookSeat(
            Authentication authentication,
            @PathVariable UUID seatId) {

        String userEmail = authentication.getName();
        bookingService.bookSeat(userEmail, seatId);

        return new ApiResponse<>("Seat booked successfully", null);
    }

}

