package com.hotelbooking.project.service;

import com.hotelbooking.project.dto.HotelBookingResponseDTO;
import com.hotelbooking.project.dto.UserBookingResponseDTO;
import com.hotelbooking.project.entity.*;
import com.hotelbooking.project.exception.BusinessException;
import com.hotelbooking.project.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final EmailService emailService;
    private final SeatScheduleRepository seatScheduleRepository;

    @Transactional
    @Override
    public void bookSeat(String userEmail, UUID scheduleId) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SeatSchedule schedule = seatScheduleRepository
                .lockById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (schedule.getBooked()) {
            throw new BusinessException("Seat already booked for this time slot");
        }

        // 1️⃣ Mark schedule as booked
        schedule.setBooked(true);
        seatScheduleRepository.save(schedule); // ✅ IMPORTANT

        // 2️⃣ Create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSeatSchedule(schedule);
        booking.setBookedAt(LocalDateTime.now());

        // snapshot
        booking.setStartTime(schedule.getStartTime());
        booking.setEndTime(schedule.getEndTime());

        bookingRepository.save(booking);

        // 3️⃣ Send mail
        sendMail(user, schedule);
    }


    private void sendMail(User user, SeatSchedule schedule) {

        Seat seat = schedule.getSeat();

        String body = """
    Hello %s,

    Your seat has been booked successfully.

    Hotel: %s
    Place: %s
    Table: %s
    Seat Number: %d

    Start Time: %s
    End Time: %s

    Thank you!
    """.formatted(
                user.getName(),
                seat.getHotel().getHotelName(),
                seat.getHotel().getPlace(),
                seat.getTableName(),
                seat.getSeatNumber(),
                schedule.getStartTime(),
                schedule.getEndTime()
        );

        emailService.sendBookingConfirmation(user.getEmail(),
                "Seat Booking Confirmation",
                body);
    }



    @Override
    public List<HotelBookingResponseDTO> getHotelBookings(String hotelEmail) {

        Hotel hotel = hotelRepository.findByEmail(hotelEmail)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        List<Booking> bookings =
                bookingRepository.findBookingsByHotelId(hotel.getId());

        return bookings.stream()
                .map(b -> {
                    Seat seat = b.getSeatSchedule().getSeat();

                    return new HotelBookingResponseDTO(
                            b.getId(),
                            seat.getTableName(),
                            seat.getSeatNumber(),
                            b.getBookedAt(),
                            b.getUser().getName(),
                            b.getUser().getEmail(),
                            b.getUser().getPhone(),
                            b.getStartTime(),
                            b.getEndTime()
                    );
                })
                .toList();
    }


    @Override
    public List<UserBookingResponseDTO> getUserBookings(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Booking> bookings =
                bookingRepository.findBookingsByUserId(user.getId());

        return bookings.stream()
                .map(b -> {
                    Seat seat = b.getSeatSchedule().getSeat();
                    Hotel hotel = seat.getHotel();

                    return new UserBookingResponseDTO(
                            b.getId(),
                            hotel.getHotelName(),
                            hotel.getPlace(),
                            seat.getTableName(),
                            seat.getSeatNumber(),
                            b.getBookedAt()
                    );
                })
                .toList();
    }



}
