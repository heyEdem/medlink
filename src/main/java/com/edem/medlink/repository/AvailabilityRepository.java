package com.edem.medlink.repository;


import com.edem.medlink.entities.Availability;
import com.edem.medlink.entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {
    @Query("SELECT COUNT(a) > 0 FROM Availability a WHERE a.doctor = :doctor " +
            "AND a.start_time < :endTime AND a.end_time > :startTime")
    boolean findOverlappingSlots(User doctor, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT a FROM Availability a WHERE a.doctor = :doctor AND a.start_time > :now")
    List<Availability> findFutureAvailabilitiesByDoctor(User doctor, LocalDateTime now);
}
