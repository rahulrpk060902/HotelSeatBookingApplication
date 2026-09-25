package com.hotelbooking.project.controller;

import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.dto.AvailableHotelSeatDto;
import com.hotelbooking.project.service.UserHotelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/hotels")
public class UserHotelController {

    private final UserHotelService userHotelService;

    public UserHotelController(UserHotelService userHotelService) {
        this.userHotelService = userHotelService;
    }

    @GetMapping("/available")
    public ApiResponse<List<AvailableHotelSeatDto>> getAvailableHotels() {
        return userHotelService.getAllAvailableHotels();
    }
}
