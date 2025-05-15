package com.edem.medlink.dto;

import java.time.LocalDateTime;

public record AvailabilityResponse(
        String id,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}