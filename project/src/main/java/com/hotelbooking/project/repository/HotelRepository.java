package com.hotelbooking.project.repository;


import com.hotelbooking.project.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {
    Optional<Hotel> findByEmail(String email);
}