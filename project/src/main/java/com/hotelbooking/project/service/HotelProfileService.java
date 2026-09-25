package com.hotelbooking.project.service;

import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.dto.HotelProfileDto;
import com.hotelbooking.project.dto.HotelUpdateRequest;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public interface HotelProfileService {
    @Nullable ApiResponse<HotelProfileDto> getHotelProfile(UUID hotelId);

    ApiResponse<HotelUpdateRequest> updateHotelProfile(UUID hotelId, HotelUpdateRequest request);
}
