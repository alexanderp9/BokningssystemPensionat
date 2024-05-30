package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.BokningDTO;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.services.BlacklistService;
import com.example.pensionatdb.services.BokningService;
import com.example.pensionatdb.services.KundService;
import com.example.pensionatdb.services.MailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequiredArgsConstructor
public class bokningController {

    private final BokningService bokningService;
    private final KundService ks;
    private final BlacklistService blacklistService;
    private final MailService mailService;

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
        if (blacklistService.checkBlacklist(blacklistService.getEmail(bokningDTO.getKundId()))) {
            log.info("Personen är blacklistad från att göra en bokning.");
            return new RedirectView("/bokning");
        }
        BokningDTO addedBokning = bokningService.addBokningFromDTO(bokningDTO);
        if (addedBokning != null) {
            log.info("Bokning lades till");

            Map<String, Object> variables = new HashMap<>();
            variables.put("namn", bokningDTO.getNamn());
            variables.put("antal", bokningDTO.getNätter());
            variables.put("startdate", bokningDTO.getStartDatum());
            variables.put("slutdate", bokningDTO.getSlutDatum());
            variables.put("totalPrice", addedBokning.getTotalPrice());

            try {
                String customerEmail = blacklistService.getEmail(bokningDTO.getKundId());
                mailService.sendMail(customerEmail, "Boknings bekräftelse", "bookingConfirmation", variables);
                log.info("Confirmation email sent to " + customerEmail);
            } catch (jakarta.mail.MessagingException e) {
                log.error("Failed to send confirmation email", e);
            }

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

    @GetMapping("/bokning/search")
    public String searchAvailableRooms(@RequestParam("startDatum") String startDatumStr,
                                       @RequestParam("slutDatum") String slutDatumStr, Model model) {
        LocalDate startDatum = LocalDate.parse(startDatumStr);
        LocalDate slutDatum = LocalDate.parse(slutDatumStr);

        List<Rum> availableRooms = bokningService.searchAvailableRoomsByDateRange(startDatum, slutDatum);
        List<BokningDTO> bokningar = bokningService.getAllBokningDTOs();

        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("bokningar", bokningar);

        return "bookingpage";
    }
}
