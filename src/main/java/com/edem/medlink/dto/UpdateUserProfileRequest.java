package com.edem.medlink.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UpdateUserProfileRequest(
        String firstName,
        String lastName,
        String contact,
        String bio,
        String clinic,
        String specialty,
        String qualification,
        LocalDateTime dob,
        String emergency_number,
        String medical_history,
        String digital_address
) {
}
// TODO: 5/3/25 add digital address