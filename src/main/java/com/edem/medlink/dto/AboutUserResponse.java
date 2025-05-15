package com.edem.medlink.dto;

import com.edem.medlink.entities.User.Roles;
import lombok.Builder;

@Builder
public record AboutUserResponse(
        String firstName,
        String lastName,
        String email,
        String contact,
        String clinic,
        String qualification,
        String bio,
        String digital_address,
        String medical_history,
        String emergency_number,
        Roles roles

) {
}
