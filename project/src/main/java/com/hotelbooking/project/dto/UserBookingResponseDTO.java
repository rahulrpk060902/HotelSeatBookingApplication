package com.hotelbooking.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserBookingResponseDTO {

    private UUID bookingId;
    private String hotelName;
    private String hotelPlace;
    private String tableName;
    private Integer seatNumber;
    private LocalDateTime bookedAt;
}
