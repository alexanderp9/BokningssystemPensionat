package com.example.pensionatdb.services;

import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.bokningRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    public boolean isRoomAvailable(Rum rum, String startSlutDatum) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        String[] parts = startSlutDatum.split("-");
        String startDateStr = parts[0].trim();
        String endDateStr = parts[1].trim();
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        List<Bokning> existingBookings = bokningRepo.findByRumAndStartSlutDatumBetween(rum, startDateStr, endDateStr);

        for (Bokning booking : existingBookings) {
            LocalDate bookingStartDate = LocalDate.parse(booking.getStartSlutDatum().substring(0, 6), formatter);
            LocalDate bookingEndDate = LocalDate.parse(booking.getStartSlutDatum().substring(7), formatter);

            if (startDate.isBefore(bookingEndDate) && endDate.isAfter(bookingStartDate)) {
                return false;
            }
        }
        return true;
    }
}

