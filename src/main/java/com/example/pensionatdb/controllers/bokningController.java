package com.example.pensionatdb.controllers;

import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.repos.bokningRepo;
import com.example.pensionatdb.services.BokningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class bokningController {

    private final bokningRepo br;
    private final BokningService bokningService;

    private static final Logger log = LoggerFactory.getLogger(bokningController.class);


@Autowired
    public bokningController(bokningRepo br, BokningService bs) {
        this.br = br;
        this.bokningService = bs;
    }


    @RequestMapping("bokning")
    public List<Bokning> getAllBokning(){
        return br.findAll();
    }

    @RequestMapping("bokning/{id}")
    public Bokning findById(@PathVariable Long id){
        return br.findById(id).get();
    }

    @PostMapping("bokning/add")
    public List<Bokning> addBokning(@RequestBody Bokning b){
        br.save(b);
        return br.findAll();
    }

    @RequestMapping("bokning/{id}/delete")
    public List<Bokning> deleteById(@PathVariable Long id){
        br.deleteById(id);
        log.info("bokning deleted with id "+ id);
        return br.findAll();
    }

    @PutMapping("/{id}/avboka")
    public ResponseEntity<Bokning> avbokaBokning(@PathVariable Long id) {
        Optional<Bokning> optionalBokning = br.findById(id);
        if (optionalBokning.isPresent()) {
            Bokning bokning = optionalBokning.get();
            bokning.setAvbokad(true);
            br.save(bokning);
            log.info("Bokning avbokad with id " + id);
            return ResponseEntity.ok(bokning);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<Bokning> updateBokning(@PathVariable Long id, @RequestBody Bokning updatedBokning) {
        Bokning updated = bokningService.updateBokning(id, updatedBokning);
        return ResponseEntity.ok(updated);
    }

}
