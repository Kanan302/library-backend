package com.example.library.repository.auth;

import com.example.library.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpCode, Long> {
    Optional<OtpCode> findByEmailAndCode(String email, String code);
    void deleteByEmail(String email);
}
