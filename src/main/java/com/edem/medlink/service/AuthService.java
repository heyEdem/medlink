package com.edem.medlink.service;

import com.edem.medlink.dto.*;

public interface AuthService {

    GenericResponseMessage signUp (SignUpRequest request);

    SignUpSuccessResponse verifyAndSignUpUser(SignUpSuccessResponse response);

    LoggedInUserResponse login(LoginRequest request);

    GenericResponseMessage resendOtp (ResendOtpRequest request);

    GenericResponseMessage resetPasswordRequest(PasswordResetRequest request);

    GenericResponseMessage resetPassword(PasswordReset request);

}
