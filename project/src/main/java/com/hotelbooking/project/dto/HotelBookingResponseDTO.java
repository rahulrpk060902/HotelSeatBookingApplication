package com.hotelbooking.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class HotelBookingResponseDTO {

    private UUID bookingId;
    private String tableName;
    private Integer seatNumber;
    private LocalDateTime bookedAt;
    private String userName;
    private String userEmail;
    private String userPhone;
}
