package com.example.pensionatdb.controllers;

import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.rumRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class rumController {

    private static final Logger log = LoggerFactory.getLogger(bokningController.class);

    private final rumRepo rr;


    public rumController(rumRepo rr) {
        this.rr = rr;
    }

    @RequestMapping("rum")
    public List<Rum> getAllRum(){
        return rr.findAll();
    }

    @RequestMapping("rum/{id}")
    public Rum findById(@PathVariable Long id){
        return rr.findById(id).get();
    }

    @PostMapping("rum/add")
    public List<Rum> addRum(@RequestBody Rum b){
        rr.save(b);
        return rr.findAll();
    }

    @RequestMapping("Rum/{id}/delete")
    public List<Rum> deleteById(@PathVariable Long id){
        rr.deleteById(id);
        log.info("kompis deleted with id "+ id);
        return rr.findAll();
    }
}
