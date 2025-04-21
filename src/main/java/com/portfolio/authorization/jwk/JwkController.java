package com.portfolio.authorization.jwk;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/.well-known")
@RequiredArgsConstructor
public class JwkController {

    private final RSAKey rsaKey;

    // JWK 형식으로 공개키 제공 (리소스 서버에서 JWT 서명 검증용)
    @GetMapping("/jwks.json")
    public Map<String, Object> getJwks() {
        return new JWKSet(rsaKey.toPublicJWK()).toJSONObject();
    }
}
