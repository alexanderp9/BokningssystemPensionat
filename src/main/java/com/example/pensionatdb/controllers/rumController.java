package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.RumDTO;

import com.example.pensionatdb.services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class rumController {

    private final RumService rumService;
    private static final Logger log = Logger.getLogger(rumController.class.getName());


    @GetMapping("/rum")
    public String getAllRum(Model model) {
        List<RumDTO> rum = rumService.getAllRumDTOs();
        model.addAttribute("rum", rum);
        log.info(rum.toString());
        return "roompage";
    }

    @GetMapping("/rum/{id}")
    public ResponseEntity<RumDTO> findById(@PathVariable Long id) {
        RumDTO rum = rumService.findRumDTOById(id);
        if (rum != null) {
            return ResponseEntity.ok(rum);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/rum/add")
    public ResponseEntity<Void> addRum(@RequestBody RumDTO rumDTO) {
        RumDTO addedRum = rumService.addRumFromDTO(rumDTO);
        if (addedRum != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/rum/{id}/delete")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        rumService.deleteRumById(id);
        return ResponseEntity.ok().build();
    }
}
