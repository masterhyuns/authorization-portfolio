package com.portfolio.authorization.resource.controller;

import com.portfolio.authorization.auth.model.Token;
import com.portfolio.authorization.auth.repository.TokenRepository;
import com.portfolio.authorization.user.model.User;
import com.portfolio.authorization.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MeController {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String accessToken = authHeader.substring(7);

        // access_token 유효성 검사
        Token token = tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));

        if (token.getExpiresAt().isBefore(Instant.now())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
        }

        // 사용자 정보 로드
        User user = userRepository.findByClientId(token.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 응답 DTO
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "name", user.getName()
        ));
    }
}
