package com.hotelbooking.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AvailableSeatDTO {

    private UUID scheduleId;
    private UUID seatId;
    private String tableName;
    private Integer seatNumber;
    private String hotelName;
    private String place;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
