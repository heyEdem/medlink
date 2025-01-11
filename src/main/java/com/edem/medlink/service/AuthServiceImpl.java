package com.edem.medlink.service;

import com.edem.medlink.dto.*;
import com.edem.medlink.repository.UserRepository;
import com.edem.medlink.service.AuthService;
import com.edem.medlink.service.OtpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final OtpService otpService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public GenericResponseMessage signUp(SignUpRequest request) {
        return null;
    }

    @Override
    public SignUpSuccessResponse verifyAndSignUpUser(SignUpSuccessResponse response) {
        return null;
    }

    @Override
    public LoggedInUserResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public GenericResponseMessage resendOtp(ResendOtpRequest request) {
        return null;
    }

    @Override
    public GenericResponseMessage resetPasswordRequest(PasswordResetRequest request) {
        return null;
    }

    @Override
    public GenericResponseMessage resetPassword(PasswordReset request) {
        return null;
    }
}
