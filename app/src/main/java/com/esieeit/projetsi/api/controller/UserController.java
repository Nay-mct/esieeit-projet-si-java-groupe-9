package com.esieeit.projetsi.api.controller;

import com.esieeit.projetsi.repository.UserRepository;
import com.esieeit.projetsi.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService(new UserRepository());
    }

    public void register(String email, String password) {
        userService.register(email, password);
        System.out.println("Utilisateur créé !");
    }

    public void login(String email, String password) {
        userService.login(email, password);
        System.out.println("Connexion réussie !");
    }
}