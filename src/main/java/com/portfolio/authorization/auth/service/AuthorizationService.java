package com.portfolio.authorization.auth.service;

import com.portfolio.authorization.auth.model.AuthCode;
import com.portfolio.authorization.auth.repository.AuthCodeRepository;
import com.portfolio.authorization.client.model.Client;
import com.portfolio.authorization.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuthCodeRepository authCodeRepository;
    private final ClientRepository clientRepository;

    public String issueAuthorizationCode(String clientId, String redirectUri) {
        // 1. client 존재 여부, redirect_uri 유효성 검증
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client"));

        if (!client.getRedirectUris().contains(redirectUri)) {
            throw new IllegalArgumentException("Invalid redirect_uri");
        }

        // 2. authorization code 생성
        String code = UUID.randomUUID().toString();

        // 3. DB 저장
        AuthCode authCode = new AuthCode(code, clientId, redirectUri, Instant.now().plusSeconds(300));
        authCodeRepository.save(authCode);

        return code;
    }
}
