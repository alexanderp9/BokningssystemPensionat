package com.example.pensionatdb.controllers;

import com.example.pensionatdb.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    // Inloggningssida
    @GetMapping("/login")
    public String loginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            return "redirect:/home"; // Redirect to home page if user is already logged in
        } else {
            return "login"; // Return the name of your Thymeleaf login page
        }
    }

    // Inloggningshantering
    @PostMapping("/login")
    public RedirectView login(String username, String password, RedirectAttributes attributes) {
        boolean loggedIn = loginService.login(username, password);
        if (loggedIn) {
            return new RedirectView("/home"); // Redirect to the home page after successful login
        } else {
            attributes.addFlashAttribute("error", "true"); // Add error attribute to indicate failed login
            return new RedirectView("/login?error=true"); // Redirect back to the login page if login fails
        }
    }

    // Utloggningshantering
    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return new RedirectView("/");
    }

    // Admin-sida
    @GetMapping(path = "/admin")
    @PreAuthorize("hasAuthority('Admin')")
    public String adminPage(Model model) {
        model.addAttribute("activeFunction", "admin");
        model.addAttribute("page", "Admin");
        return "security/admin";
    }

    // Customer-sida


    // Startsida efter inloggning
    @GetMapping("/home")
    public String homePage() {
        return "home"; // Return the name of the Thymeleaf home page for logged in users
    }
}
