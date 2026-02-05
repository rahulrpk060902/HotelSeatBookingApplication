package com.hotelbooking.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SeatResponseDTO {

    private UUID seatId;
    private String tableName;
    private Integer seatNumber;
    private String hotelName;
    private String hotelPlace;
}

