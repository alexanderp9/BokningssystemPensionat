package com.example.pensionatdb.controllers;

import com.example.pensionatdb.models.User;
import com.example.pensionatdb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAuthority('admin')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin')")
    public String listUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User roles: " + authentication.getAuthorities());
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users"; // Skapar och visar en sida som listar alla användare
    }



    @GetMapping("/users/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/create_user"; // Skapar och visar en sida för att skapa en ny användare
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin/users"; // Omdirigerar till listan med användare efter att en ny användare har skapats
    }

    @GetMapping("/users/{id}/edit")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id).orElse(null);
        model.addAttribute("user", user);
        return "admin/edit_user"; // Skapar och visar en sida för att redigera en befintlig användare
    }

    @PostMapping("/users/{id}/edit")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/admin/users"; // Omdirigerar till listan med användare efter att en användare har uppdaterats
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users"; // Omdirigerar till listan med användare efter att en användare har raderats
    }
}
