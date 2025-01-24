package com.edem.medlink.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for resending OTP")
public record ResendOtpRequest (
        String email,

        @Schema(description = "Type of OTP request. Can be either 'create' or 'reset'",
                allowableValues = {"create", "reset"}
        )
        String type
) {
}
