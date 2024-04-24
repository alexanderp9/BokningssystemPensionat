package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.BokningDTO;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.bokningRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BokningService {

    private final bokningRepo bokningRepo;

    @Autowired
    public BokningService(bokningRepo bokningRepo) {
        this.bokningRepo = bokningRepo;
    }


    private BokningDTO convertToBokningDTO(Bokning bokning) {
        BokningDTO dto = new BokningDTO();
        dto.setId(bokning.getId());
        dto.setNätter(bokning.getNätter());
        dto.setStartSlutDatum(bokning.getStartSlutDatum());
        dto.setKundNamn(bokning.getKund().getNamn());
        dto.setRumId(bokning.getRum().getId());
        return dto;
    }


    public List<BokningDTO> getAllBokningDTOs() {
        List<Bokning> bokningar = bokningRepo.findAll();
        return bokningar.stream()
                .map(this::convertToBokningDTO)
                .collect(Collectors.toList());
    }


    public BokningDTO findBokningDTOById(Long id) {
        Optional<Bokning> optionalBokning = bokningRepo.findById(id);
        return optionalBokning.map(this::convertToBokningDTO).orElse(null);
    }


    public BokningDTO addBokning(Bokning bokning) {
        Bokning savedBokning = bokningRepo.save(bokning);
        return convertToBokningDTO(savedBokning);
    }


    public void deleteBokning(Long id) {
        bokningRepo.deleteById(id);
    }


    public void avbokaBokning(Long id) {
        Optional<Bokning> optionalBokning = bokningRepo.findById(id);
        if (optionalBokning.isPresent()) {
            Bokning bokning = optionalBokning.get();
            bokning.setAvbokad(true);
            bokningRepo.save(bokning);
        }
    }


    public BokningDTO updateBokning(Long id, Bokning updatedBokning) {
        Optional<Bokning> optionalBokning = bokningRepo.findById(id);
        if (optionalBokning.isPresent()) {
            Bokning bokning = optionalBokning.get();
            bokning.setNätter(updatedBokning.getNätter());
            bokning.setStartSlutDatum(updatedBokning.getStartSlutDatum());
            bokning.setKund(updatedBokning.getKund());
            bokning.setRum(updatedBokning.getRum());
            Bokning savedBokning = bokningRepo.save(bokning);
            return convertToBokningDTO(savedBokning);
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

