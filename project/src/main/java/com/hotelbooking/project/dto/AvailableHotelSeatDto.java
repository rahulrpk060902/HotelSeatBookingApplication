package com.hotelbooking.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableHotelSeatDto {

    private UUID hotelId;
    private String hotelName;
    private String place;

    private UUID seatId;
    private String tableName;
    private Integer seatNumber;

    private UUID scheduleId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
