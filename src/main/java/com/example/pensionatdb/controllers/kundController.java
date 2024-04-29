package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.DetailedKundDto;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.services.BokningService;
import com.example.pensionatdb.services.KundService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class kundController {

    private static final Logger log = LoggerFactory.getLogger(bokningController.class);

    private final BokningService bs;
    private final KundService ks;


    @GetMapping("/kund")
    public String getAllKund(Model model) {
        List<DetailedKundDto> customers = ks.getAllKund();
        model.addAttribute("customers", customers);
        return "customerpage";
    }

    @RequestMapping("/kund/{id}")
    public Kund findById(@PathVariable Long id){
        return ks.findById(id).get();
    }


    @PostMapping("/kund/add")
    public RedirectView addKund(@ModelAttribute Kund b){
        if (b.getNamn()==null || b.getAdress()==null){
            log.info("lagt till kund");
            ks.save(b);
            return new RedirectView("/kund",true);
        }
        else {
            log.info("du angav inga värden");
            return new RedirectView("/kund",true);
        }
    }



    @PostMapping("/kund/delete")
    public RedirectView deleteKund(@RequestParam Long customerId) {
        Optional<Kund> kundOptional = ks.findById(customerId);
        if (kundOptional.isPresent()) {
            Kund kund = kundOptional.get();
            List<Bokning> bokningar = bs.findBokningarByKund(kund);
            if (bokningar.isEmpty()) {
                ks.deleteById(customerId);
                log.info("Kund bortagen med id:  " + customerId);
            } else {
                log.warn("Kund med id: " + customerId + " kan inte tas bort då den finns i en bokning.");
            }
        }
        return new RedirectView("/kund", true);
    }

    @PostMapping("/kund/update")
    public RedirectView updateraKund(@RequestParam Long customerId, @RequestParam(required = false) String namn, @RequestParam(required = false) String adress, RedirectAttributes redirectAttributes) {
        if (namn != null || adress != null) {
            ks.updateKundNameById(customerId, namn);
            ks.updateKundAddressById(customerId, adress);
            log.info("Kund med id:  " + customerId +  " har uppdaterats") ;
            redirectAttributes.addFlashAttribute("error","Kund har uppdateras");

        }
        return new RedirectView("/kund", true);
    }



    @RequestMapping("/kundNameChange/{id}/{namn}")
    public List<DetailedKundDto> updateName(@PathVariable Long id, @PathVariable String namn){
        ks.updateKundNameById(id, namn);
        return ks.getAllKund();
    }

    @RequestMapping("/kundAddressChange/{id}/{adress}")
    public List<DetailedKundDto> updateAddress(@PathVariable Long id, @PathVariable String adress){
        ks.updateKundAddressById(id, adress);
        return ks.getAllKund();
    }

}