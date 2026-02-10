package com.hotelbooking.project.repository;

import com.hotelbooking.project.entity.SeatSchedule;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeatScheduleRepository extends JpaRepository<SeatSchedule, UUID> {

    List<SeatSchedule> findBySeat_Id(UUID seatId);

    @Query("""
    SELECT s FROM SeatSchedule s
    WHERE s.endTime > CURRENT_TIMESTAMP
""")
    List<SeatSchedule> findAllFutureSchedules();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM SeatSchedule s WHERE s.id = :id")
    Optional<SeatSchedule> lockById(@Param("id") UUID id);


    @Query("""
SELECT s FROM SeatSchedule s
WHERE s.seat.id = :seatId
AND s.startTime < :endTime
AND s.endTime > :startTime
""")
    List<SeatSchedule> findOverlappingSchedules(
            UUID seatId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

}
