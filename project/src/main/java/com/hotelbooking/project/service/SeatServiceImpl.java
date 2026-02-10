package com.hotelbooking.project.service;


import com.hotelbooking.project.dto.*;
import com.hotelbooking.project.entity.Hotel;
import com.hotelbooking.project.entity.Seat;
import com.hotelbooking.project.entity.SeatSchedule;
import com.hotelbooking.project.repository.HotelRepository;
import com.hotelbooking.project.repository.SeatRepository;
import com.hotelbooking.project.repository.SeatScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final HotelRepository hotelRepository;
    private final SeatScheduleRepository seatScheduleRepository;

    @Override
    public void createSeat(String hotelEmail, CreateSeatRequest request) {

        Hotel hotel = hotelRepository.findByEmail(hotelEmail)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // 🔒 Validate time
        if (request.getStartTime().isAfter(request.getEndTime()) ||
                request.getStartTime().isEqual(request.getEndTime())) {
            throw new RuntimeException("Invalid time range");
        }

        // 🔍 Check if seat already exists
        Seat seat = seatRepository
                .findByHotel_IdAndTableNameAndSeatNumber(
                        hotel.getId(),
                        request.getTableName(),
                        request.getSeatNumber()
                )
                .orElse(null);

        if (seat != null) {
            List<SeatSchedule> overlaps =
                    seatScheduleRepository.findOverlappingSchedules(
                            seat.getId(),
                            request.getStartTime(),
                            request.getEndTime()
                    );

            if (!overlaps.isEmpty()) {
                throw new RuntimeException(
                        "Seat already exists for the given time range"
                );
            }
        }

        // 🪑 Create seat if not exists
        if (seat == null) {
            seat = new Seat();
            seat.setHotel(hotel);
            seat.setTableName(request.getTableName());
            seat.setSeatNumber(request.getSeatNumber());
            seatRepository.save(seat);
        }

        // 🕒 Create schedule
        SeatSchedule schedule = new SeatSchedule();
        schedule.setSeat(seat);
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setBooked(false);

        seatScheduleRepository.save(schedule);
    }




    @Override
    public List<SeatResponse> getSeatsByHotel(String hotelEmail) {

        Hotel hotel = hotelRepository.findByEmail(hotelEmail)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        List<Seat> seats = seatRepository.findByHotelId(hotel.getId());

        return seats.stream().map(seat -> {

            List<SeatScheduleDTO> schedules =
                    seatScheduleRepository.findBySeat_Id(seat.getId())
                            .stream()
                            .map(schedule -> new SeatScheduleDTO(
                                    schedule.getId(),
                                    schedule.getStartTime(),
                                    schedule.getEndTime(),
                                    schedule.getBooked()
                            ))
                            .toList();

            // ✅ CALCULATE availability from schedules
            boolean available = schedules.stream()
                    .noneMatch(SeatScheduleDTO::isBooked);

            return new SeatResponse(
                    seat.getId(),
                    seat.getTableName(),
                    seat.getSeatNumber(),
                    available,   // ✅ CORRECT
                    schedules
            );

        }).toList();
    }

    @Override
    public List<AvailableSeatDTO> getAllAvailableSeats() {

        List<SeatSchedule> schedules =
                seatScheduleRepository.findAllFutureSchedules();

        return schedules.stream()
                .map(schedule -> new AvailableSeatDTO(
                        schedule.getId(),
                        schedule.getSeat().getId(),
                        schedule.getSeat().getTableName(),
                        schedule.getSeat().getSeatNumber(),
                        schedule.getSeat().getHotel().getHotelName(),
                        schedule.getSeat().getHotel().getPlace(),
                        schedule.getStartTime(),
                        schedule.getEndTime()
                ))
                .toList();
    }



}
