package com.edem.medlink.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpDoctor(

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

        @NotBlank
        @NotNull
        String contact,

        @NotBlank
        @NotNull
        String clinic,

        @NotBlank
        @NotNull
        String specialty,

        @NotBlank
        @NotNull
        String qualification,

        @NotBlank
        @NotNull
        String bio

) {
}
