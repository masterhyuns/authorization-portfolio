package com.portfolio.authorization.auth.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    // JWK로부터 주입된 비공개키 포함 RSAKey
    private final RSAKey rsaKey;

    // access_token 발급 (RS256 비대칭 서명 방식)
    public String generateToken(String subject, Duration expiresIn) {
        Instant now = Instant.now();

        try {
            JWSSigner signer = new RSASSASigner(rsaKey.toPrivateKey());

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(subject)
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(now.plus(expiresIn)))
                    .claim("client_id", subject)
                    .build();

            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                    .keyID(rsaKey.getKeyID())
                    .type(JOSEObjectType.JWT)
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claims);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("JWT 생성 실패", e);
        }
    }
}
