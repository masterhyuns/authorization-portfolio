package com.portfolio.authorization.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "auth_codes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthCode {

    @Id
    private String code;

    private String clientId;

    private String redirectUri;

    private Instant expiresAt;
}
