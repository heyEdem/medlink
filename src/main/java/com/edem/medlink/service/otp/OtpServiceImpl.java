package com.edem.medlink.service.otp;

import com.edem.medlink.dto.OtpEmailTemplate;
import com.edem.medlink.entities.Otp.Otp;
import com.edem.medlink.entities.Otp.OtpType;
import com.edem.medlink.exception.VerificationFailedException;
import com.edem.medlink.repository.OtpRepository;
import com.edem.medlink.util.OtpMailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.edem.medlink.util.Validator.*;

@Slf4j
@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final OtpMailSender sender;

    private static final int OTP_LENGTH = 6;

    @Override
    public void generateAndSendOtp(String email, OtpType type) {
        String code = generator();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);
        LocalDateTime now = LocalDateTime.now();

        Optional<Otp> existingOtp = otpRepository.findByEmail(email);
        Otp otp;

        if (existingOtp.isPresent()) {
            log.info("Updating existing OTP for email: {}", email);
            otp = existingOtp.get();
            otp.setCode(code);
            otp.setType(type);
            otp.setExpiry(expiry);
            otp.setCreatedAt(now);
        } else {
            otp = Otp.builder()
                    .code(code)
                    .type(type)
                    .expiry(expiry)
                    .email(email)
                    .createdAt(now)
                    .build();
        }

        OtpEmailTemplate template = OtpEmailTemplate.builder()
                .to(email)
                .otpType(type)
                .otp(code)
                .build();


        sender.send(template);

        otpRepository.save(otp);
    }

    @Override
    public boolean isValidOtp(String code, String email) {
        Otp otp = otpRepository.findByCode(code).orElseThrow(() -> new VerificationFailedException(OTP_VERIFICATION_FAILED_MESSAGE));

        if (!Objects.equals(otp.getEmail(), email)) throw new VerificationFailedException(OTP_VERIFICATION_FAILED_MESSAGE);

        boolean isOtpExpired = isOtpExpired(otp);

        return !isOtpExpired;

    }

    @Override
    public void invalidateOtp(String code) {
        Otp otp = otpRepository.findByCode(code).orElseThrow(()-> new VerificationFailedException(OTP_VERIFICATION_FAILED_MESSAGE));
        otp.setExpiry(LocalDateTime.now());
        otpRepository.save(otp);
    }

    private boolean isOtpExpired(Otp otp) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiredAt = otp.getExpiry();

        return currentTime.isAfter(expiredAt);
    }

    private String generator(){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i <= OTP_LENGTH; i ++){
            builder.append(secureRandom.nextInt(10));
        }
        return builder.toString();
    }

}
