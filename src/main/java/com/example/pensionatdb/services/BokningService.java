package com.example.pensionatdb.services;

import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.repos.bokningRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class BokningService {

    private final bokningRepo bokningRepo;

    @Autowired
    public BokningService(bokningRepo bokningRepo) {
        this.bokningRepo = bokningRepo;
    }

    public Bokning updateBokning(Long id, Bokning updatedBokning) {
        Optional<Bokning> optionalBokning = bokningRepo.findById(id);
        if (optionalBokning.isPresent()) {
            Bokning bokning = optionalBokning.get();
            bokning.setNätter(updatedBokning.getNätter());
            bokning.setStartSlutDatum(updatedBokning.getStartSlutDatum());
            bokning.setKund(updatedBokning.getKund());
            bokning.setRum(updatedBokning.getRum());
            return bokningRepo.save(bokning);
        } else {
            throw new RuntimeException("Bokning not found with id: " + id);
        }
    }
}