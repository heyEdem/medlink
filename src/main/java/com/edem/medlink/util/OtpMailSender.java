package com.edem.medlink.util;


import com.edem.medlink.dto.OtpEmailTemplate;
import com.edem.medlink.entities.Otp.OtpType;
import com.edem.medlink.exception.VerificationFailedException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import static com.edem.medlink.util.Validator.NOT_FOUND_MSG;
import static com.edem.medlink.util.Validator.VERIFICATION_FAILED;

@Component
@RequiredArgsConstructor
public class OtpMailSender implements EmailService<OtpEmailTemplate> {
    private final JavaMailSender javaMailSender;
    private final ITemplateEngine templateEngine;


    @Override
    public void send(OtpEmailTemplate request) {
        sendOtpMail(request.to(), request.otp(), request.otpType() );
    }

    private void sendOtpMail(String to, String otp, OtpType otpType) {

        try {
            if (!isValidEmail(to)) throw new EntityNotFoundException(NOT_FOUND_MSG);

            Context context = new Context();
            context.setVariable("otp", otp);

            String templateType = otpType == OtpType.CREATE ? "email-otp-template" : "email-reset-template";
            String process = templateEngine.process(templateType, context);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject("OTP password for MedLink");
            helper.setText(process, true);
            helper.setTo(to);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new VerificationFailedException(VERIFICATION_FAILED);
        }
    }

    @Override
    public boolean isValidEmail(String email) {
        return EmailService.super.isValidEmail(email);
    }
}
