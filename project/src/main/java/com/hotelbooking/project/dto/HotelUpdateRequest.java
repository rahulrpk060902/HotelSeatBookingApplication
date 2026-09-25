package com.hotelbooking.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelUpdateRequest {
    private String hotelName;
    private String place;
    private String email;
    private String phone;
}
