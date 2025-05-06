package com.edem.medlink.repository;

import com.edem.medlink.entities.Otp.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByCode(String code);
    Optional<Otp> findByEmail(String email);
}
