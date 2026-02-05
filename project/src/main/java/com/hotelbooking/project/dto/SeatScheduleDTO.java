package com.hotelbooking.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SeatScheduleDTO {

    private UUID scheduleId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
