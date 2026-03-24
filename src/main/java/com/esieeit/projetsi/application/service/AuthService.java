package com.esieeit.projetsi.application.service;

import com.esieeit.projetsi.api.dto.auth.AuthResponse;
import com.esieeit.projetsi.api.dto.auth.LoginRequest;
import com.esieeit.projetsi.api.dto.auth.RegisterRequest;
import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.exception.UnauthorizedException;
import com.esieeit.projetsi.domain.model.User;
import com.esieeit.projetsi.infrastructure.repository.UserJpaRepository;
import java.util.Locale;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserJpaRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String normalizedUsername = normalizeUsername(request.getUsername());
        String normalizedEmail = normalizeEmail(request.getEmail());

        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new BusinessRuleException("Username deja utilise");
        }
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new BusinessRuleException("Email deja utilise");
        }

        String passwordHash = passwordEncoder.encode(request.getPassword());
        User user = new User(normalizedEmail, normalizedUsername, passwordHash, UserRole.USER);
        User savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String normalizedLogin = request.getLogin().trim().toLowerCase(Locale.ROOT);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(normalizedLogin, request.getPassword()));
            User user = (User) authentication.getPrincipal();
            return toResponse(user);
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException("Identifiants invalides");
        }
    }

    private AuthResponse toResponse(User user) {
        String token = jwtService.generateToken(user);
        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getJwtExpirationMs(),
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getSecurityRole());
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private String normalizeUsername(String username) {
        return username.trim().toLowerCase(Locale.ROOT);
    }
}
