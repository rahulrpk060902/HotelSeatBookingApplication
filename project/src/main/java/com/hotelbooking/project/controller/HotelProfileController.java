package com.hotelbooking.project.controller;

import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.dto.HotelProfileDto;
import com.hotelbooking.project.dto.HotelUpdateRequest;
import com.hotelbooking.project.service.HotelProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/hotel/dashboard/profile")
public class HotelProfileController {

    private final HotelProfileService hotelProfileService;

    public HotelProfileController(HotelProfileService hotelProfileService) {
        this.hotelProfileService = hotelProfileService;
    }

    @GetMapping("/{hotelId}")
    public ApiResponse<HotelProfileDto> viewProfile(
            @PathVariable UUID hotelId) {

        return hotelProfileService.getHotelProfile(hotelId);
    }

    @PutMapping("/{hotelId}")
    public ApiResponse<HotelUpdateRequest> updateHotelProfile(
            @PathVariable UUID hotelId,
            @RequestBody HotelUpdateRequest request) {

       return hotelProfileService.updateHotelProfile(hotelId, request);
    }
}

