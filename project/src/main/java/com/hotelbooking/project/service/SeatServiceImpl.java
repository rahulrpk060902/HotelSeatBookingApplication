package com.hotelbooking.project.service;


import com.hotelbooking.project.dto.CreateSeatRequest;
import com.hotelbooking.project.dto.SeatResponse;
import com.hotelbooking.project.dto.SeatResponseDTO;
import com.hotelbooking.project.entity.Hotel;
import com.hotelbooking.project.entity.Seat;
import com.hotelbooking.project.repository.HotelRepository;
import com.hotelbooking.project.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final HotelRepository hotelRepository;

    @Override
    public void createSeat(String hotelEmail, CreateSeatRequest request) {

        Hotel hotel = hotelRepository.findByEmail(hotelEmail)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Seat seat = new Seat();
        seat.setHotel(hotel);
        seat.setTableName(request.getTableName());
        seat.setSeatNumber(request.getSeatNumber());
        seat.setAvailable(true);

        seatRepository.save(seat);
    }

    @Override
    public List<SeatResponse> getSeatsByHotel(String hotelEmail) {

        Hotel hotel = hotelRepository.findByEmail(hotelEmail)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        return seatRepository.findByHotelId(hotel.getId())
                .stream()
                .map(seat -> new SeatResponse(
                        seat.getId(),
                        seat.getTableName(),
                        seat.getSeatNumber(),
                        seat.getAvailable()
                ))
                .toList();
    }

    @Override
    public List<SeatResponseDTO> getAllAvailableSeats() {
        List<Seat> seats = seatRepository.findAllAvailableSeats();

        return seats.stream()
                .map(seat -> new SeatResponseDTO(
                        seat.getId(),
                        seat.getTableName(),
                        seat.getSeatNumber(),
                        seat.getHotel().getHotelName(),
                        seat.getHotel().getPlace()
                ))
                .toList();

    }


}
