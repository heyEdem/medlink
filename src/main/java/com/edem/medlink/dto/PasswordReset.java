package com.edem.medlink.dto;

public record PasswordReset(
        String email,
        String password,

        String code
) {
}
