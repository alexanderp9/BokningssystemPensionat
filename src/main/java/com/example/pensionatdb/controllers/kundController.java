package com.example.pensionatdb.controllers;

import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.repos.kundRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class kundController {

    private static final Logger log = LoggerFactory.getLogger(bokningController.class);

    private final kundRepo kr;

    public kundController(kundRepo kr) {
        this.kr = kr;
    }

    @RequestMapping("kund")
    public List<Kund> getAllKund(){
        return kr.findAll();
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

    @RequestMapping("kund/{id}/delete")
    public List<Kund> deleteById(@PathVariable Long id){
        kr.deleteById(id);
        log.info("kund deleted with id "+ id);
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
