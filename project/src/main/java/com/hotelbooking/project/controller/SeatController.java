package com.hotelbooking.project.controller;

import com.hotelbooking.project.dto.*;
import com.hotelbooking.project.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/hotel/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public ApiResponse<String> createSeat(
            Authentication authentication,
            @RequestBody CreateSeatRequest request) {
        String email = authentication.getName();
        seatService.createSeat(email, request);
        return new ApiResponse<>("Seat created successfully", null);
    }


    @GetMapping
    public ApiResponse<List<SeatResponse>> getSeats(Authentication authentication) {
        String email = authentication.getName();
        List<SeatResponse> seats = seatService.getSeatsByHotel(email);
        return new ApiResponse<>("Seats fetched successfully", seats);
    }


//    @GetMapping("/all-available-seats")
//    public ApiResponse<List<AvailableSeatDTO>> getAvailableSeats() {
//
//        List<AvailableSeatDTO> seats = seatService.getAllAvailableSeats();
//
//        return new ApiResponse<>("Available seats fetched successfully", seats);
//    }
}

