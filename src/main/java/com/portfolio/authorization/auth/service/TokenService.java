package com.portfolio.authorization.auth.service;

import com.portfolio.authorization.auth.dto.TokenResponse;
import com.portfolio.authorization.auth.model.AuthCode;
import com.portfolio.authorization.auth.model.Token;
import com.portfolio.authorization.auth.repository.AuthCodeRepository;
import com.portfolio.authorization.auth.repository.TokenRepository;
import com.portfolio.authorization.auth.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AuthCodeRepository authCodeRepository;
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;
    @Transactional
    public TokenResponse issueAccessToken(String code, String clientId, String redirectUri) {
        // 1. auth code 검증
        AuthCode authCode = authCodeRepository.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("Invalid code"));

        if (!authCode.getClientId().equals(clientId) || !authCode.getRedirectUri().equals(redirectUri)) {
            throw new IllegalArgumentException("Invalid client or redirect_uri");
        }

        if (authCode.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Authorization code expired");
        }

        // 2. access_token / refresh_token 발급
        String accessToken = jwtUtil.generateToken(clientId, Duration.ofMinutes(10));
        String refreshToken = jwtUtil.generateToken(clientId, Duration.ofDays(30));

        // 3. 토큰 저장
        tokenRepository.save(new Token(accessToken, refreshToken, clientId, Instant.now().plusSeconds(600)));

        // 4. auth code 삭제 (일회용)
        authCodeRepository.delete(authCode);

        return new TokenResponse(accessToken, refreshToken, "bearer", 600);
    }
    @Transactional
    public TokenResponse refreshAccessToken(String refreshToken, String clientId) {
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh_token"));

        if (!token.getClientId().equals(clientId)) {
            throw new IllegalArgumentException("Client mismatch");
        }

        // 기존 토큰 만료 체크는 생략 가능 (refresh_token은 long-lived)

        // 새 access_token 생성
        String newAccessToken = jwtUtil.generateToken(clientId, Duration.ofMinutes(10));

        // DB 갱신
        token.changeToken(newAccessToken, Instant.now().plusSeconds(600));
        tokenRepository.save(token);

        return new TokenResponse(
                newAccessToken,
                token.getRefreshToken(),
                "bearer",
                600
        );
    }

    public boolean revoke(String token) {
        Optional<Token> byAccess = tokenRepository.findByAccessToken(token);
        if (byAccess.isPresent()) {
            tokenRepository.delete(byAccess.get());
            return true;
        }

        Optional<Token> byRefresh = tokenRepository.findByRefreshToken(token);
        if (byRefresh.isPresent()) {
            tokenRepository.delete(byRefresh.get());
            return true;
        }

        return false;
    }
}
