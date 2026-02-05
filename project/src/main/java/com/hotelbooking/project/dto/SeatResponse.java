package com.hotelbooking.project.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SeatResponse {
    private UUID id;
    private String tableName;
    private Integer seatNumber;
    private Boolean available;
}