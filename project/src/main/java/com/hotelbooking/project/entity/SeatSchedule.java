package com.hotelbooking.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class SeatSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Seat seat;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean booked = false;

}
