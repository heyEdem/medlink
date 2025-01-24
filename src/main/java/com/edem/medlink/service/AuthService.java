package com.edem.medlink.service;

import com.edem.medlink.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<GenericResponseMessage> signUp (SignUpRequest request);

    SignUpSuccessResponse verifyAndSignUpUser(VerifyOtpDto otpDto);

    LoggedInUserResponse login(LoginRequest request);

    GenericResponseMessage resendOtp (ResendOtpRequest request);

    GenericResponseMessage resetPasswordRequest(PasswordResetRequest request);

    GenericResponseMessage resetPassword(PasswordReset request);

}
