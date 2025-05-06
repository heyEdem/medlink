package com.edem.medlink.service.auth;

import com.edem.medlink.dto.*;
import com.edem.medlink.entities.Otp.OtpType;
import com.edem.medlink.entities.User.Roles;
import com.edem.medlink.entities.User.User;
import com.edem.medlink.exception.DuplicateEmailException;
import com.edem.medlink.exception.UserNotFoundException;
import com.edem.medlink.exception.VerificationFailedException;
import com.edem.medlink.repository.UserRepository;
import com.edem.medlink.security.JwtService;
import com.edem.medlink.security.SecurityUser;
import com.edem.medlink.service.otp.OtpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.edem.medlink.util.Validator.*;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final OtpService otpService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<GenericResponseMessage> signUp(SignUpRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.email());

        if (byEmail.isPresent()) {
            User testUser = byEmail.get();

            if (testUser.isVerified()) { //if user exists & is not verified let them request new otp
                throw new DuplicateEmailException(DUPLICATE_EMAIL);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new GenericResponseMessage(ACCOUNT_EXIST_ALREADY));
            }

        } else {

            User user = User.builder()
                    .email(request.email())
                    .firstname(request.firstName())
                    .lastname(request.lastName())
                    .password(passwordEncoder.encode(request.password()))
                    .contact(request.contact())
                    .role(Roles.PATIENT)
                    .build();

            otpService.generateAndSendOtp(user.getEmail(), OtpType.CREATE);
            userRepository.save(user);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GenericResponseMessage(TOKEN_SENT_MSG));
    }
    @Override
    public ResponseEntity<GenericResponseMessage> signUpDoctor(SignUpDoctor request) {
        Optional<User> byEmail = userRepository.findByEmail(request.email());

        if (byEmail.isPresent()) {
            User testUser = byEmail.get();

            if (testUser.isVerified()) { //if user exists & is not verified let them request new otp
                throw new DuplicateEmailException(DUPLICATE_EMAIL);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new GenericResponseMessage(ACCOUNT_EXIST_ALREADY));
            }

        } else {

            User user = User.builder()
                    .email(request.email())
                    .firstname(request.firstName())
                    .lastname(request.lastName())
                    .password(passwordEncoder.encode(request.password()))
                    .contact(request.contact())
                    .clinic(request.clinic())
                    .specialty(request.specialty())
                    .qualification(request.qualification())
                    .bio(request.bio())
                    .role(Roles.DOCTOR)
                    .build();

            otpService.generateAndSendOtp(user.getEmail(), OtpType.CREATE);
            userRepository.save(user);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GenericResponseMessage(TOKEN_SENT_MSG));
    }

    @Override
    public SignUpSuccessResponse verifyAndSignUpUser(VerifyOtpDto otp) {
        boolean otpValid = otpService.isValidOtp(otp.code(), otp.email());

        if(!otpValid) {
            throw new VerificationFailedException(OTP_NOT_VERIFIED);
        }
        User user = userRepository.findByEmail(otp.email()).orElseThrow(()-> new VerificationFailedException(OTP_NOT_VERIFIED));
        user.setVerified(true);
        userRepository.save(user);

        otpService.invalidateOtp(otp.code());

        String token = jwtService.generateToken(new SecurityUser(user));

        return SignUpSuccessResponse.builder()
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .token(token)
                .build();

    }

    @Override
    public LoggedInUserResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        User user = userRepository.findByEmail(request.email()).orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND));

        String token  = jwtService.generateToken(new SecurityUser(user));

        return LoggedInUserResponse.builder()
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .token(token)
                .roles(user.getRole())
                .build();
    }

    @Override
    public GenericResponseMessage resendOtp(ResendOtpRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));

        otpService.generateAndSendOtp(user.getEmail(), OtpType.valueOf(request.type().toUpperCase()));

        return new GenericResponseMessage(TOKEN_SENT_MSG);
    }

    @Override
    public GenericResponseMessage resetPasswordRequest(PasswordResetRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.email());

        if(byEmail.isEmpty()) throw new UserNotFoundException(USER_NOT_FOUND);

        otpService.generateAndSendOtp(request.email(), OtpType.RESET);
        return new GenericResponseMessage(TOKEN_SENT_MSG);
    }

    @Override
    public GenericResponseMessage resetPassword(PasswordReset request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new VerificationFailedException(PASSWORD_MISMATCH);
        }

        boolean otpValid = otpService.isValidOtp(request.code(), request.email());

        if (!otpValid) throw new VerificationFailedException(OTP_VERIFICATION_FAILED_MESSAGE);

        User user = userRepository.findByEmail(request.email()).orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        otpService.invalidateOtp(request.code());

        return new GenericResponseMessage(RESET_PASSWORD_SUCC);

    }
}
