package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.customersDTO;
import com.example.pensionatdb.models.customers;
import com.example.pensionatdb.repos.customersRepo;
import com.example.pensionatdb.services.customersService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AvtalsKundController {
    private static final Logger log = LoggerFactory.getLogger(bokningController.class);

    private final customersService customersService;



    @GetMapping("/avtalskunder")
    public String getAllContractCustomers(Model model) {
        // Hämta avtalskunder från databasen eller annan lagring
        List<customersDTO> contractCustomers = customersService.getAllCustomers()
                .stream()
                .map(customersService::convertToCustomersDTO)
                .collect(Collectors.toList());
        // Lägg till avtalskunderna i modellen för att skicka till användargränssnittet
        model.addAttribute("contractCustomers", contractCustomers);
        // Returnera vyn för att visa avtalskunderna
        return "avtalskunder";
    }

    // Kontrollmetod för att visa Avtalskundssidan för en specifik kund baserat på ID
    @GetMapping("/avtalskund")
    public String showCustomerDetails(@RequestParam("id") Long customerId, Model model) {
        // Hämta kundinformation från databasen baserat på kundens ID
        customersDTO customer = customersService.getCustomerById(customerId);

        // Lägg till kundinformation i modellen för att skicka till användargränssnittet
        model.addAttribute("customer", customer);

        // Returnera vyn för att visa Avtalskundssidan
        return "avtalskundSida";
    }

    @GetMapping("/search/avtalskunder")
    public String searchAvtalskunder(Model model, @RequestParam(defaultValue = "") String q,
                                     @RequestParam(defaultValue = "companyName") String sortCol,
                                     @RequestParam(defaultValue = "ASC") String sortOrder) {
        q = q.trim();
        model.addAttribute("q", q);
        log.info("Searching with q={}, sortCol={}, sortOrder={}", q, sortCol, sortOrder);

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortCol);
        log.info("Using sort: {}", sort);

        List<customers> contractCustomers;
        if (!q.isEmpty()) {
            contractCustomers = customersService.findAllByCompanyNameContainsOrContactNameContainsOrCountryContainsDTO(q, q, q, sort);
        } else {
            contractCustomers = Collections.emptyList(); // Empty list for empty search
        }

        model.addAttribute("contractCustomers", contractCustomers);
        return "avtalskunder";
    }
}


