package com.hotelbooking.project.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelProfileDto {

    private UUID id;
    private String hotelName;
    private String place;
    private String email;
    private String phone;
}

