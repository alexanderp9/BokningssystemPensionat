package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.DetailedKundDto;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.services.impl.bokningServiceImpl;
import com.example.pensionatdb.services.impl.kundServiceImpl;
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

    private final bokningServiceImpl bs;
    private final kundServiceImpl ks;


    @RequestMapping("kund")
    public List<DetailedKundDto> getAllKund(){
        return ks.getAllKund();
    }

    @RequestMapping("kund/{id}")
    public Kund findById(@PathVariable Long id){
        return ks.findById(id).get();
    }

    @PostMapping("kund/add")
    public List<DetailedKundDto> addKund(@RequestBody Kund b){
        ks.save(b);
        return ks.getAllKund();
    }



    @DeleteMapping("/kund/{id}/delete")
    public List<DetailedKundDto> deleteById(@PathVariable Long id) {
        Optional<Kund> kundOptional = ks.findById(id);
        if (kundOptional.isPresent()) {
            Kund kund = kundOptional.get();
            List<Bokning> bokningar = bs.findBokningarByKund(kund);
            if (bokningar.isEmpty()) {
                ks.deleteById(id);
                log.info("kund deleted with id " + id);
            }
            else log.warn("kund with id " + id + " cannot be deleted as they have a bokning .");
        }
        return ks.getAllKund();
    }


    @RequestMapping("kundNameChange/{id}/{namn}")
    public List<DetailedKundDto> updateName(@PathVariable Long id, @PathVariable String namn){
        ks.updateKundNameById(id, namn);
        return ks.getAllKund();
    }

    @RequestMapping("kundAddressChange/{id}/{adress}")
    public List<DetailedKundDto> updateAddress(@PathVariable Long id, @PathVariable String adress){
        ks.updateKundAddressById(id, adress);
        return ks.getAllKund();
    }

}
