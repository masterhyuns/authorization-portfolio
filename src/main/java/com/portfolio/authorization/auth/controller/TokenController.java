package com.portfolio.authorization.auth.controller;

import com.portfolio.authorization.auth.dto.TokenResponse;
import com.portfolio.authorization.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> issueToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "refresh_token", required = false) String refreshToken,
            @RequestParam("client_id") String clientId,
            @RequestParam(value = "redirect_uri", required = false) String redirectUri
    ) {
        if ("authorization_code".equals(grantType)) {
            if (code == null || redirectUri == null) {
                return ResponseEntity.badRequest().body("Missing required parameters for authorization_code grant");
            }
            TokenResponse token = tokenService.issueAccessToken(code, clientId, redirectUri);
            return ResponseEntity.ok(token);
        }

        if ("refresh_token".equals(grantType)) {
            if (refreshToken == null) {
                return ResponseEntity.badRequest().body("Missing refresh_token for refresh_token grant");
            }
            TokenResponse token = tokenService.refreshAccessToken(refreshToken, clientId);
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.badRequest().body("Unsupported grant_type: " + grantType);
    }

    @PostMapping("/revoke")
    public ResponseEntity<?> revokeToken(
            @RequestParam("token") String token
    ) {
        boolean revoked = tokenService.revoke(token);
        return revoked ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().body("Token not found");
    }
}
