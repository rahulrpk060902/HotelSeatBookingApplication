package com.hotelbooking.project.service;

import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.dto.AvailableHotelSeatDto;

import java.util.List;

public interface UserHotelService {

    ApiResponse<List<AvailableHotelSeatDto>> getAllAvailableHotels();
}
