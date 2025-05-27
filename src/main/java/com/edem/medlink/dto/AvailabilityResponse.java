package com.edem.medlink.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AvailabilityResponse(
        String id,
        UUID doctor_id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean booked
) {
}