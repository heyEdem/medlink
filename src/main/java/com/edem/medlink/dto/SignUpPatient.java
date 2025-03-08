package com.edem.medlink.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record SignUpPatient(
        @NotBlank
        @NotNull
        String firstName,

        @NotBlank
        @NotNull
        String lastName,

        @NotBlank
        @NotNull
        String email,

        @NotBlank
        @NotNull
        String password,

        String contact,


        @NotNull
        LocalDate dob,

        @NotBlank
        @NotNull
        String emergency_number ,

        @NotBlank
        @NotNull
        String medical_history
) {
}
