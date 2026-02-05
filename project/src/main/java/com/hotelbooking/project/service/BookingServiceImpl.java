package com.hotelbooking.project.service;

import com.hotelbooking.project.dto.HotelBookingResponseDTO;
import com.hotelbooking.project.dto.UserBookingResponseDTO;
import com.hotelbooking.project.entity.Booking;
import com.hotelbooking.project.entity.Hotel;
import com.hotelbooking.project.entity.Seat;
import com.hotelbooking.project.entity.User;
import com.hotelbooking.project.exception.BusinessException;
import com.hotelbooking.project.repository.BookingRepository;
import com.hotelbooking.project.repository.HotelRepository;
import com.hotelbooking.project.repository.SeatRepository;
import com.hotelbooking.project.repository.UserRepository;
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

    @Override
    @Transactional
    public void bookSeat(String userEmail, UUID seatId) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Seat seat = bookingRepository.lockSeatById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (!seat.getAvailable()) {
            throw new BusinessException("Seat already booked");
        }

        seat.setAvailable(false);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSeat(seat);
        booking.setBookedAt(LocalDateTime.now());

        bookingRepository.save(booking);
        try {
            String subject = "Seat Booking Confirmation";

            String body = """
            Hello %s,

            Your seat has been booked successfully.

            Hotel: %s
            Place: %s
            Table: %s
            Seat Number: %d
            Booking Time: %s

            Thank you for choosing us!
            """.formatted(
                    user.getName(),
                    seat.getHotel().getHotelName(),
                    seat.getHotel().getPlace(),
                    seat.getTableName(),
                    seat.getSeatNumber(),
                    booking.getBookedAt()
            );

            emailService.sendBookingConfirmation(
                    user.getEmail(),
                    subject,
                    body
            );

        } catch (Exception e) {
            // LOG ONLY — DO NOT BREAK BOOKING
            System.out.println("Email sending failed: " + e.getMessage());
        }
    }

    @Override
    public List<HotelBookingResponseDTO> getHotelBookings(String hotelEmail) {

        Hotel hotel = hotelRepository.findByEmail(hotelEmail)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        List<Booking> bookings =
                bookingRepository.findBookingsByHotelId(hotel.getId());

        return bookings.stream()
                .map(b -> new HotelBookingResponseDTO(
                        b.getId(),
                        b.getSeat().getTableName(),
                        b.getSeat().getSeatNumber(),
                        b.getBookedAt(),
                        b.getUser().getName(),
                        b.getUser().getEmail(),
                        b.getUser().getPhone()
                ))
                .toList();
    }

    @Override
    public List<UserBookingResponseDTO> getUserBookings(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Booking> bookings =
                bookingRepository.findBookingsByUserId(user.getId());

        return bookings.stream()
                .map(b -> new UserBookingResponseDTO(
                        b.getId(),
                        b.getSeat().getHotel().getHotelName(),
                        b.getSeat().getHotel().getPlace(),
                        b.getSeat().getTableName(),
                        b.getSeat().getSeatNumber(),
                        b.getBookedAt()
                ))
                .toList();
    }


}
