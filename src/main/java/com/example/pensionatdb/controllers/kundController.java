package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.DetailedKundDto;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.repos.bokningRepo;
import com.example.pensionatdb.repos.kundRepo;
import com.example.pensionatdb.services.kundService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class kundController {

    private static final Logger log = LoggerFactory.getLogger(bokningController.class);

    private final kundRepo kr;
    private final bokningRepo br;

    private final kundService ks;


    @RequestMapping("kund")
    public List<DetailedKundDto> getAllKund(){
        return ks.getAllKnud();
    }

    @RequestMapping("kund/{id}")
    public Kund findById(@PathVariable Long id){
        return kr.findById(id).get();
    }

    @PostMapping("kund/add")
    public List<Kund> addKund(@RequestBody Kund b){
        kr.save(b);
        return kr.findAll();
    }



    @RequestMapping("/kund/{id}/delete")
    public List<Kund> deleteById(@PathVariable Long id) {
        Optional<Kund> kundOptional = kr.findById(id);
        if (kundOptional.isPresent()) {
            Kund kund = kundOptional.get();
            List<Bokning> bokningar = br.findByKund(kund);
            if (bokningar.isEmpty()) {
                kr.deleteById(id);
                log.info("Kund deleted with id " + id);
            }
            else log.warn("Kund with id " + id + " cannot be deleted as they have a bokning .");
        }
        return kr.findAll();
    }


    @RequestMapping("kundNameChange/{id}/{namn}")
    public List<Kund> updateName(@PathVariable Long id,@PathVariable String namn){
        kr.updateNameById(id, namn);
        return kr.findAll();
    }

    @RequestMapping("kundAddressChange/{id}/{adress}")
    public List<Kund> updateAddress(@PathVariable Long id,@PathVariable String adress){
        kr.updateAddressById(id, adress);
        return kr.findAll();
    }

}
