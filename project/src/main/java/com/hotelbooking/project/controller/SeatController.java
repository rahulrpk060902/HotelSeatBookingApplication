package com.hotelbooking.project.controller;

import com.hotelbooking.project.dto.CreateSeatRequest;
import com.hotelbooking.project.dto.SeatResponse;
import com.hotelbooking.project.dto.SeatResponseDTO;
import com.hotelbooking.project.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hotel/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<String> createSeat(
            Authentication authentication,
            @RequestBody CreateSeatRequest request) {

        String email = authentication.getName(); // ← EMAIL
        seatService.createSeat(email, request);

        return ResponseEntity.ok("Seat created successfully");
    }

    @GetMapping
    public ResponseEntity<List<SeatResponse>> getSeats(
            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(seatService.getSeatsByHotel(email));
    }

    @GetMapping("/all-available-seats")
    public ResponseEntity<List<SeatResponseDTO>> getAvailableSeats() {
        return ResponseEntity.ok(seatService.getAllAvailableSeats());
    }
}

