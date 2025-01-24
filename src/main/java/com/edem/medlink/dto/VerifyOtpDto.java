package com.edem.medlink.dto;

public record VerifyOtpDto (
        String code,
        String email
        ){
}
