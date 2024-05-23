package com.example.pensionatdb.controllers;

import com.example.pensionatdb.services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public RedirectView login(@RequestParam String username, @RequestParam String password) {
        boolean loggedIn = loginService.login(username, password);
        if (loggedIn) {
            return new RedirectView("/index"); // Redirect to the index page after login
        } else {
            // Handle failed login
            return new RedirectView("/login"); // Redirect back to the login page if login fails
        }
    }

    @PostMapping("/register")
    public void register(@RequestParam String username, @RequestParam String password) {
        loginService.register(username, password);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/"; // Omdirigera till startsidan eller annan lämplig sida efter utloggning
    }
}
