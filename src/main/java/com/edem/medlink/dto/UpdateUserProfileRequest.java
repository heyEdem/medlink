package com.edem.medlink.dto;

public record UpdateUserProfileRequest(
        String firstName,
        String lastName,
        String bio,
        String specialty,
        String qualification,
        String contact,
        String emergency_number
) {
}
