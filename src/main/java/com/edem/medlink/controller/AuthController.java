package com.edem.medlink.controller;

import com.edem.medlink.dto.*;
import com.edem.medlink.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.security.PublicKey;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "User sign up",
            method = "POST"
    )

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericResponseMessage> register(@Valid @RequestBody SignUpRequest request){
        return authService.signUp(request);
    }
@Operation(
            summary = "Doctor sign up",
            method = "POST"
    )

    @PostMapping("/signup-doc")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericResponseMessage> register_doctor(@Valid @RequestBody SignUpDoctor request){
        return authService.signUpDoctor(request);
    }

    @Operation(
            summary = "verify otp and set user to verified",
            method = "POST"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/verify-otp")
    public SignUpSuccessResponse verifyUser(@RequestBody VerifyOtpDto otp){
        return authService.verifyAndSignUpUser(otp);
    }


    @Operation(
            summary = "login with email and password",
            method = "POST"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/login")
    public LoggedInUserResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

    @Operation(
            summary = "request for otp resend",
            method = "POST"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/resend-otp")
    public GenericResponseMessage resendOtp (@RequestBody ResendOtpRequest request){
        return authService.resendOtp(request);
    }


    @Operation(
            summary = "request to reset password",
            method = "POST"
    )
    @PostMapping("/request-password-reset")
    public GenericResponseMessage resetPassword(@RequestBody PasswordResetRequest request){
        return authService.resetPasswordRequest(request);
    }

    @Operation(
            summary = "reset password with email code and new password",
            method = "POST"
    )
    @PostMapping("/reset-password")
    public GenericResponseMessage resetPassword(@RequestBody PasswordReset request){
        System.out.println("request to change password");
        return authService.resetPassword(request);
    }

}
