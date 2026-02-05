package com.hotelbooking.project.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSeatRequest {
    private String tableName;
    private Integer seatNumber;
}