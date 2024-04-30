package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.RumDTO;
import com.example.pensionatdb.services.RumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rum")
public class rumController {

    private final RumService rumService;

    @Autowired
    public rumController(RumService rumService) {
        this.rumService = rumService;
    }

    @GetMapping
    public ResponseEntity<List<RumDTO>> getAllRum() {
        List<RumDTO> rum = rumService.getAllRumDTOs();
        return ResponseEntity.ok(rum);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RumDTO> findById(@PathVariable Long id) {
        RumDTO rum = rumService.findRumDTOById(id);
        if (rum != null) {
            return ResponseEntity.ok(rum);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addRum(@RequestBody RumDTO rumDTO) {
        // Kontrollera om rummet är dubbelrum och tilldela maxExtrasängar
        if ("dubbelrum".equals(rumDTO.getRumTyp())) {
            // Tilldela maxExtrasängar för dubbelrum
            if (rumDTO.getExtraSäng() > 2) {
                return ResponseEntity.badRequest().build(); // Felaktigt antal extrasängar för dubbelrum
            }
            rumDTO.setMaxExtrasängar(2); // Max antal extrasängar för dubbelrum
        } else {
            // Om rummet inte är dubbelrum, sätt maxExtrasängar till 0
            rumDTO.setMaxExtrasängar(0);
        }

        RumDTO addedRum = rumService.addRumFromDTO(rumDTO);
        if (addedRum != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        rumService.deleteRumById(id);
        return ResponseEntity.ok().build();
    }
}