package com.example.pensionatdb.controllers;

import com.example.pensionatdb.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private LoginService loginService;

    // Metod för att visa registreringssidan
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Returnera namnet på din HTML-sida för registrering
    }

    // Metod för att hantera registrering av användare
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        // Anropa LoginService för att registrera användaren
        loginService.register(username, password);

        // Returnera vyn för bekräftelse av registreringen
        return "registration_success";
    }
}
