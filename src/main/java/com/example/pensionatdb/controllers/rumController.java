package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.RumDTO;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.rumRepo;
import com.example.pensionatdb.services.RumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rum")
public class rumController {

    private static final Logger log = LoggerFactory.getLogger(rumController.class);

    private final rumRepo rr;
    private final RumService rumService;

    @Autowired
    public rumController(rumRepo rr, RumService rumService) {
        this.rr = rr;
        this.rumService = rumService;
    }

    @RequestMapping()
    public List<Rum> getAllRum(){
        return rr.findAll();
    }

    @RequestMapping("/{id}")
    public Rum findById(@PathVariable Long id){
        return rr.findById(id).get();
    }

    @PostMapping("/add")
    public List<Rum> addRum(@RequestBody Rum rum) {
        rr.save(rum);
        log.info("Rum added: " + rum.toString());
        return rr.findAll();
    }

    @RequestMapping("/{id}/delete")
    public List<Rum> deleteById(@PathVariable Long id){
        rr.deleteById(id);
        log.info("Rum deleted with id "+ id);
        return rr.findAll();
    }

    @GetMapping("/availableRooms")
    public List<RumDTO> getAvailableRooms(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("numberOfPersons") int numberOfPersons) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return rumService.searchAvailableRooms(start, end, numberOfPersons);
    }
}
