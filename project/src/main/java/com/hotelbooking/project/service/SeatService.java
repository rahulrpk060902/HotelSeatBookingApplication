package com.hotelbooking.project.service;


import com.hotelbooking.project.dto.CreateSeatRequest;
import com.hotelbooking.project.dto.SeatResponse;
import com.hotelbooking.project.dto.SeatResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SeatService {

    void createSeat(String hotelEmail, CreateSeatRequest request);

    List<SeatResponse> getSeatsByHotel(String hotelEmail);

    List<SeatResponseDTO> getAllAvailableSeats();
}
