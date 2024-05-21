package com.example.pensionatdb.controllers;

import com.example.pensionatdb.services.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller

public class BlacklistController {

    private final BlacklistService blacklistService;

    @Autowired
    public BlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    @GetMapping("/blacklist")
    public String returnBlacklistAdmin(Model model) {
        model.addAttribute("blacklisted", blacklistService.getAllBlacklisted());
        return "blacklistAdmin";
    }

    @PostMapping("/blacklist/add")
    public RedirectView addBlacklist(@RequestParam String email, @RequestParam boolean ok) {
        blacklistService.addBlacklistMail(email, ok);
        return new RedirectView("/blacklist");
    }

    @PostMapping("/blacklist/update")
    public RedirectView updateBlacklist(@RequestParam String email, @RequestParam boolean ok) {
        blacklistService.updateBlacklistMail(email, ok);
        return new RedirectView("/blacklist");
    }
}