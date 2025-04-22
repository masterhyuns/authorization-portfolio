package com.portfolio.authorization.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Controller
public class LoginController {
    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String client_id,
            @RequestParam String redirect_uri,
            @RequestParam(required = false) String scope,
            @RequestParam(required = false) String state,
            HttpServletRequest request) {

        // 인증 로직
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        // ❗ 반드시 명시적으로 세션에도 저장
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

        // 파라미터를 다시 넘겨서 authorize 로 이동
        StringBuilder authorizeUrl = new StringBuilder("/authorize");
        authorizeUrl.append("?client_id=").append(UriUtils.encode(client_id, StandardCharsets.UTF_8));
        authorizeUrl.append("&redirect_uri=").append(UriUtils.encode(redirect_uri, StandardCharsets.UTF_8));
        if (scope != null) authorizeUrl.append("&scope=").append(UriUtils.encode(scope, StandardCharsets.UTF_8));
        if (state != null) authorizeUrl.append("&state=").append(UriUtils.encode(state, StandardCharsets.UTF_8));

        return "redirect:" + authorizeUrl;
    }
}
