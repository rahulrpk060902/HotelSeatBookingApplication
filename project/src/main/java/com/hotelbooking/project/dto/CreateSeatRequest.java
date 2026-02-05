package com.hotelbooking.project.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateSeatRequest {
    private String tableName;
    private Integer seatNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}