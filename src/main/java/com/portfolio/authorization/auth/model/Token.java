package com.portfolio.authorization.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "tokens")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

    private String refreshToken;

    private String clientId;

    private Instant expiresAt;

    public void changeToken(String accessToken, Instant expiresAt){
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
    }

    public Token(String accessToken, String refreshToken, String clientId, Instant expiresAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.clientId = clientId;
        this.expiresAt = expiresAt;
    }
}
