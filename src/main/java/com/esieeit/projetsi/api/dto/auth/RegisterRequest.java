package com.esieeit.projetsi.api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "username est obligatoire")
    @Size(min = 3, max = 50, message = "username doit contenir entre 3 et 50 caracteres")
    private String username;

    @NotBlank(message = "email est obligatoire")
    @Email(message = "email invalide")
    @Size(max = 150, message = "email ne doit pas depasser 150 caracteres")
    private String email;

    @NotBlank(message = "password est obligatoire")
    @Size(min = 8, max = 100, message = "password doit contenir entre 8 et 100 caracteres")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
