package com.hotelbooking.project.repository;

import com.hotelbooking.project.entity.Booking;
import com.hotelbooking.project.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.id = :seatId")
    Optional<Seat> lockSeatById(UUID seatId);

    @Query("""
    SELECT b FROM Booking b
    JOIN b.seatSchedule ss
    JOIN ss.seat s
    WHERE s.hotel.id = :hotelId
    """)
    List<Booking> findBookingsByHotelId(@Param("hotelId") UUID hotelId);


    @Query("""
        SELECT b FROM Booking b
        WHERE b.user.id = :userId
        ORDER BY b.bookedAt DESC
    """)
    List<Booking> findBookingsByUserId(UUID userId);
}

