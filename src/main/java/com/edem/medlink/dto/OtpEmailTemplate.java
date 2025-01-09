package com.edem.medlink.dto;

import com.edem.medlink.entities.Otp.OtpType;
import lombok.Builder;

@Builder
public record OtpEmailTemplate(
        String to,
        String otp,
        OtpType otpType

) {
}
