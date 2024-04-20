package com.example.pensionatdb.controllers;

import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.repos.bokningRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class bokningController {

    private final bokningRepo br;

    private static final Logger log = LoggerFactory.getLogger(bokningController.class);


    public bokningController(bokningRepo br) {
        this.br = br;
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
        log.info("kompis deleted with id "+ id);
        return br.findAll();
    }

}
