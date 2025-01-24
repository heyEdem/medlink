package com.edem.medlink.dto;

import lombok.Builder;

@Builder
public record SignUpSuccessResponse (
        String name,
        String email,
        String token
){
}
