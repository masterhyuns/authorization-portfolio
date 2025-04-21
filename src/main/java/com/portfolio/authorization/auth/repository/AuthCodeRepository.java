package com.portfolio.authorization.auth.repository;

import com.portfolio.authorization.auth.model.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthCodeRepository extends JpaRepository<AuthCode, String> {
}
