package com.esieeit.projetsi.service;

import com.esieeit.projetsi.domain.model.User;
import com.esieeit.projetsi.exception.BusinessException;
import com.esieeit.projetsi.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // US-01 Inscription
    public User register(String email, String password) {

        if (email == null || email.isBlank()) {
            throw new BusinessException("Email invalide");
        }

        if (password == null || password.length() < 6) {
            throw new BusinessException("Mot de passe trop court");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new BusinessException("Email déjà utilisé");
        }

        User user = new User(email, password);
        userRepository.save(user);

        return user;
    }

    // US-02 Connexion
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Utilisateur non trouvé"));

        if (!user.checkPassword(password)) {
            throw new BusinessException("Mot de passe incorrect");
        }

        return user;
    }
}