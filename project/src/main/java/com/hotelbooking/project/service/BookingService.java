package com.hotelbooking.project.service;


import com.hotelbooking.project.dto.HotelBookingResponseDTO;
import com.hotelbooking.project.dto.UserBookingResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    void bookSeat(String userEmail, UUID seatId);

    List<HotelBookingResponseDTO> getHotelBookings(String hotelEmail);

    List<UserBookingResponseDTO> getUserBookings(String userEmail);


}