package com.hotelbooking.project.repository;

import com.hotelbooking.project.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {

    List<Seat> findByHotelId(UUID hotelId);

    List<Seat> findByAvailableTrue();

    @Query("""
        SELECT s FROM Seat s
        WHERE s.available = true
        ORDER BY s.hotel.hotelName
    """)
    List<Seat> findAllAvailableSeats();
}