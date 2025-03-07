package com.edem.medlink.dto;

import lombok.Builder;

@Builder
public record SignUpSuccessResponse (
        String firstname,
        String lastname,
        String email,
        String token
){
}
