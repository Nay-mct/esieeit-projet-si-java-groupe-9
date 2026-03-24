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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserJpaRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
        String normalizedLogin = request.getLogin().trim();
        User user = userRepository.findByEmailOrUsername(normalizedLogin.toLowerCase(Locale.ROOT), normalizedLogin)
                .orElseThrow(() -> new UnauthorizedException("Identifiants invalides"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Identifiants invalides");
        }

        return toResponse(user);
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
