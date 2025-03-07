package com.edem.medlink.dto;

import com.edem.medlink.entities.User.Roles;
import lombok.Builder;

@Builder
public record LoggedInUserResponse(
        String firstname,
        String lastname,
        String email,
        String token,
        Roles roles
) {
}
