package com.edem.medlink.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.edem.medlink.util.Validator.*;
import static com.edem.medlink.util.Validator.PASSWORD_NOT_NULL;

public record LoginRequest(

        @NotBlank(message = FIELD_IS_REQUIRED)
        @NotNull(message = FIELD_IS_REQUIRED)
        String email,

        @NotBlank (message = FIELD_IS_REQUIRED)
        @NotNull(message = FIELD_IS_REQUIRED)
        String password
) {
}
