package com.edem.medlink.service;

import com.edem.medlink.entities.Otp.OtpType;

public interface OtpService {

    void generateAndSendOtp(String email, OtpType type);

    boolean isValidOtp(String code, String email);

    void invalidateOtp(String code);
}
