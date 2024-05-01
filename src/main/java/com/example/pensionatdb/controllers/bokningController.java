package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.BokningDTO;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.services.BokningService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@Controller()
@RequiredArgsConstructor
public class bokningController {

    private final BokningService bokningService;

    private static final Logger log = LoggerFactory.getLogger(bokningController.class);


    @GetMapping("/bokning")
    public String getAllBokning(Model model) {
        List<BokningDTO> bokningar = bokningService.getAllBokningDTOs();
        model.addAttribute("bokningar", bokningar);
        log.info(bokningar.toString());
        return "bookingpage";
    }


    @GetMapping("/bokning/{id}")
    public ResponseEntity<BokningDTO> findById(@PathVariable Long id) {
        BokningDTO bokning = bokningService.findBokningDTOById(id);
        if (bokning != null) {
            return ResponseEntity.ok(bokning);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/bokning/add")
    public RedirectView addBokning(@ModelAttribute("bokningDTO") BokningDTO bokningDTO) {
        BokningDTO addedBokning = bokningService.addBokningFromDTO(bokningDTO);
        if (addedBokning != null) {
            log.info("Bokning lades till");
            return new RedirectView("/bokning", true);
        } else {
            log.info("Rummet är upptaget för det datumet");
            return new RedirectView("/bokning", true);
        }
    }

    @PostMapping("/bokning/delete")
    public RedirectView deleteBooking(@RequestParam Long bookingId) {
        bokningService.deleteBokning(bookingId);
        return new RedirectView("/bokning", true);

    }


    @PostMapping("/bokning/update")
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
