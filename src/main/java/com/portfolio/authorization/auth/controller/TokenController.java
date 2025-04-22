package com.portfolio.authorization.auth.controller;

import com.portfolio.authorization.auth.dto.TokenResponse;
import com.portfolio.authorization.auth.service.TokenService;
import com.portfolio.authorization.common.exception.CustomException;
import com.portfolio.authorization.common.response.ApiResponse;
import com.portfolio.authorization.common.response.ApiResponseCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<ApiResponse<TokenResponse>> issueToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "refresh_token", required = false) String refreshToken,
            @RequestParam("client_id") String clientId,
            @RequestParam(value = "redirect_uri", required = false) String redirectUri
    ) {
        if ("authorization_code".equals(grantType)) {
            if(code == null){
                throw new CustomException(ApiResponseCode.INVALID_CODE);
            }
            if(redirectUri == null){
                throw new CustomException(ApiResponseCode.INVALID_REDIRECT_URL);
            }

            TokenResponse token = tokenService.issueAccessToken(code, clientId, redirectUri);
            return ApiResponse.OK(token);
        }

        if ("refresh_token".equals(grantType)) {
            if (refreshToken == null) {
                throw new CustomException(ApiResponseCode.INVALID_TOKEN);
            }
            TokenResponse token = tokenService.refreshAccessToken(refreshToken, clientId);
            return ApiResponse.OK(token);
        }

        throw new CustomException(ApiResponseCode.INVALID_GRANT_TYPE);
    }

    @PostMapping("/revoke")
    public ResponseEntity<ApiResponse<Void>> revokeToken(
            @RequestParam("token") String token,
            HttpServletRequest request, HttpServletResponse response
    ) {
        tokenService.revoke(token);
        return ApiResponse.OK();
    }
}
