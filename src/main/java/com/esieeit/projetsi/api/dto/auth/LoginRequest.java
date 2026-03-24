package com.esieeit.projetsi.api.dto.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "login est obligatoire")
    @Size(max = 150, message = "login ne doit pas depasser 150 caracteres")
    @JsonAlias("usernameOrEmail")
    private String login;

    @NotBlank(message = "password est obligatoire")
    @Size(max = 100, message = "password ne doit pas depasser 100 caracteres")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
