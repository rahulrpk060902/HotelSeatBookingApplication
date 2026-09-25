package com.hotelbooking.project.service;

import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.dto.AvailableHotelSeatDto;
import com.hotelbooking.project.repository.SeatScheduleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserHotelServiceImpl implements UserHotelService {

    private final SeatScheduleRepository seatScheduleRepository;

    public UserHotelServiceImpl(SeatScheduleRepository seatScheduleRepository) {
        this.seatScheduleRepository = seatScheduleRepository;
    }

    @Override
    public ApiResponse<List<AvailableHotelSeatDto>> getAllAvailableHotels() {

        List<AvailableHotelSeatDto> list =
                seatScheduleRepository.findAllAvailableHotelSeats();

        return new ApiResponse<>(
                "Available hotels fetched successfully",
                list
        );
    }
}
