package com.example.pensionatdb.controllers;

import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.repos.kundRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kund")
@Validated
public class kundController {

    private static final Logger log = LoggerFactory.getLogger(bokningController.class);

    private final kundRepo kr;

    public kundController(kundRepo kr) {
        this.kr = kr;
    }

    @RequestMapping()
    public List<Kund> getAllKund(){
        return kr.findAll();
    }

    @RequestMapping("/{id}")
    public Kund findById(@PathVariable Long id){
        return kr.findById(id).get();
    }

    @PostMapping("/add")
    public List<Kund> addKund(@RequestBody Kund b){
        kr.save(b);
        return kr.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<Kund> optionalKund = kr.findById(id);
        if (optionalKund.isPresent()) {
            Kund kund = optionalKund.get();
            if (kr.canDeleteKund(kund)) {
                kr.deleteById(id);
                log.info("Kund deleted with id " + id);
                return ResponseEntity.ok().build();
            } else {
                log.info("Kunden har bokningar och kan inte tas bort.");
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
