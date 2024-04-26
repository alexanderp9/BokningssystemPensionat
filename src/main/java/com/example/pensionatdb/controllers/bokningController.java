package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.BokningDTO;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.services.BokningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/bokning")
public class bokningController {

    private final BokningService bokningService;

    private static final Logger log = LoggerFactory.getLogger(bokningController.class);

    @Autowired
    public bokningController(BokningService bokningService) {
        this.bokningService = bokningService;
    }

    @GetMapping
    public ResponseEntity<List<BokningDTO>> getAllBokning() {
        List<BokningDTO> bokningar = bokningService.getAllBokningDTOs();
        return ResponseEntity.ok(bokningar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BokningDTO> findById(@PathVariable Long id) {
        BokningDTO bokning = bokningService.findBokningDTOById(id);
        if (bokning != null) {
            return ResponseEntity.ok(bokning);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addBokning(@RequestBody BokningDTO bokningDTO) {
        BokningDTO addedBokning = bokningService.addBokningFromDTO(bokningDTO);
        if (addedBokning != null) {
            log.info("Bokning lyckades");
            return ResponseEntity.ok().build();
        } else {
            log.info("Rummet du ville boka är upptaget för perioden.");
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        bokningService.deleteBokning(id);
        log.info("Bokning deleted with id " + id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/avboka")
    public ResponseEntity<Void> avbokaBokning(@PathVariable Long id) {
        bokningService.avbokaBokning(id);
        log.info("Bokning avbokad with id " + id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<BokningDTO> updateBokning(@PathVariable Long id, @RequestBody Bokning updatedBokning) {
        BokningDTO updated = bokningService.updateBokningFromEntity(id, updatedBokning);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Rum>> searchAvailableRooms(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam int nätter
    ) {
        List<Rum> availableRooms = bokningService.searchAvailableRooms(startDate, endDate, nätter);
        if (!availableRooms.isEmpty()) {
            return ResponseEntity.ok(availableRooms);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
