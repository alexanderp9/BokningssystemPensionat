package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.customersDTO;
import com.example.pensionatdb.services.customersService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AvtalsKundController {
    private static final Logger log = LoggerFactory.getLogger(bokningController.class);

    private final customersService cs;

    @GetMapping("/avtalskunder")
    public String getAllCustomers(Model model) {
        List<customersDTO> contractCustomers = cs.getAllCustomers()
                .stream()
                .map(cs::convertToCustomersDTO)
                .collect(Collectors.toList());
        model.addAttribute("contractCustomers", contractCustomers);
        return "customerpage";
    }
}
