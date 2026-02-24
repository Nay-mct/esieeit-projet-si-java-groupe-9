package com.esieeit.projetsi;

import com.esieeit.projetsi.api.controller.UserController;

public class App {

    public static void main(String[] args) {

        UserController controller = new UserController();

        controller.register("test@mail.com", "123456");
        controller.login("test@mail.com", "123456");
    }
}