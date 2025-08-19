package com.example.library.repository.auth;

import com.example.library.entity.auth.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpCode, Long> {
    Optional<OtpCode> findByCode(String code);

    void deleteByEmail(String email);

    Optional<OtpCode> findTopByOrderByExpirationTimeDesc();

}
