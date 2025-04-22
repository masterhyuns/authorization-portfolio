package com.portfolio.authorization.auth.controller;

import com.portfolio.authorization.auth.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/authorize")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<?> authorize(
            @RequestParam String client_id,
            @RequestParam String redirect_uri,
            @RequestParam(required = false) String scope,
            @RequestParam(required = false) String state
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            // 인증되지 않은 상태 → 로그인 페이지로 리다이렉트
            StringBuilder redirectUrl = new StringBuilder("/login");
            redirectUrl.append("?client_id=").append(UriUtils.encode(client_id, StandardCharsets.UTF_8));
            redirectUrl.append("&redirect_uri=").append(UriUtils.encode(redirect_uri, StandardCharsets.UTF_8));
            if (scope != null) {
                redirectUrl.append("&scope=").append(UriUtils.encode(scope, StandardCharsets.UTF_8));
            }
            if (state != null) {
                redirectUrl.append("&state=").append(UriUtils.encode(state, StandardCharsets.UTF_8));
            }

            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(redirectUrl.toString()))
                    .build();
        }

        // 1. client_id/redirect_uri 유효성 검사
        // 2. 로그인된 사용자 정보 확인 (Session or JWT)
        // 3. Authorization Code 생성 및 저장
        // 4. redirect_uri 로 code 와 함께 리다이렉트

        String code = authorizationService.issueAuthorizationCode(client_id, redirect_uri);
        URI redirect = URI.create(redirect_uri + "?code=" + code + (state != null ? "&state=" + state : ""));
        return ResponseEntity.status(HttpStatus.FOUND).location(redirect).build();
    }
}
