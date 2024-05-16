package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.DetailedKundDto;
import com.example.pensionatdb.dtos.customersDTO;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.services.BokningService;
import com.example.pensionatdb.services.KundService;
import com.example.pensionatdb.services.customersService;
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
    private final customersService cs;


//model används för att skicka data från javakoden till HTML-Sidan
    @GetMapping("/kund")
    public String getAllKund(Model model) {
        //gettAllKund retunerar end lista med alla kunder
        List<DetailedKundDto> customers = ks.getAllKund();
        //här läggs listan till i model för att visa den på HTML-Sidan
        model.addAttribute("customers", customers);
        return "customerpage"; //retunerar namnet på sidan man ska till
    }

//model används för att skicka data till html-sidan for att rendera information
//på användarens webbläsare

    @PostMapping("/kund/add")
    //@ModelAttribute används för att binda data till en modell
    //binda inkommande data från html till kund
    public RedirectView addKund(@ModelAttribute Kund b){
            log.info("lagt till kund");
            ks.save(b);
            return new RedirectView("/kund",true);
            //tar tillbaka till kund
    }


    @PostMapping("/kund/delete")
    public RedirectView deleteKund(@RequestParam Long customerId, RedirectAttributes ra) {
        //metoden retunerar ett objekt av typen RedirectView som anger vilken URL omdirigeras till
        //tar ett objekt av typen RedirectAttributes som används för att skicka data till nästa sida
        Optional<Kund> kundOptional = ks.findById(customerId);
        //findById retunerar en kund (optional betyder antigen kund eller null)
        if (kundOptional.isPresent()) {
            Kund kund = kundOptional.get();
            List<Bokning> bokningar = bs.findBokningarByKund(kund);
            //hämtar bokningar för kunden
            if (bokningar.isEmpty()) {
                ks.deleteById(customerId);
                log.info("Kund bortagen med id:  " + customerId);
            } else {
                log.warn("Kund med id: " + customerId + " kan inte tas bort då den finns i en bokning.");
                ra.addFlashAttribute("popupMessage", "Kund med id: " + customerId + " kan ej tas bort då personen finns i bokning");
            }
        }
        return new RedirectView("/kund", true); //omdirigerar användaren till /kund
    }



    @PostMapping("/kund/update")
    public RedirectView updateraKund(@RequestParam Long customerId,
                                     @RequestParam(required = false) String namn,
                                     @RequestParam(required = false) String adress,
                                     RedirectAttributes redirectAttributes) {
                                    //används för att skicka tillbaka data till nästa sida
        if (namn != null || adress != null) {
            ks.updateKundNameById(customerId, namn);
            ks.updateKundAddressById(customerId, adress);
            log.info("Kund med id:  " + customerId +  " har uppdaterats") ;
            redirectAttributes.addFlashAttribute("error","Kund har uppdateras");//meddelande

        }
        return new RedirectView("/kund", true);
        //omdirigerar användaren till /kund
    }


}