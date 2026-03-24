package com.esieeit.projetsi.api.controller;

import com.esieeit.projetsi.api.dto.auth.AuthResponse;
import com.esieeit.projetsi.api.dto.auth.CurrentUserResponse;
import com.esieeit.projetsi.api.dto.auth.LoginRequest;
import com.esieeit.projetsi.api.dto.auth.RegisterRequest;
import com.esieeit.projetsi.application.service.AuthService;
import com.esieeit.projetsi.domain.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public CurrentUserResponse me(@AuthenticationPrincipal User user) {
        return CurrentUserResponse.from(user);
    }
}
