package com.edem.medlink.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateAvailabilityRequest(
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
