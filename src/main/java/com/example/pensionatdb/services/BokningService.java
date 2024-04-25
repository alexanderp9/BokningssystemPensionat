package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.BokningDTO;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.bokningRepo;
import com.example.pensionatdb.repos.kundRepo;
import com.example.pensionatdb.repos.rumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BokningService {

    private final bokningRepo bokningRepo;
    private final kundRepo kundRepo;
    private final rumRepo rumRepo;

    @Autowired
    public BokningService(bokningRepo bokningRepo, kundRepo kundRepo, rumRepo rumRepo) {
        this.bokningRepo = bokningRepo;
        this.kundRepo = kundRepo;
        this.rumRepo = rumRepo;
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

    private Bokning updateBokningDetails(Bokning existingBokning, Bokning updatedBokning) {
        existingBokning.setNätter(updatedBokning.getNätter());
        existingBokning.setStartSlutDatum(updatedBokning.getStartSlutDatum());
        existingBokning.setKund(updatedBokning.getKund());
        existingBokning.setRum(updatedBokning.getRum());
        return bokningRepo.save(existingBokning);
    }

    public Bokning convertToEntity(BokningDTO bokningDTO) {
        Kund kund = kundRepo.findById(bokningDTO.getKundId()).orElse(null);
        Rum rum = rumRepo.findById(bokningDTO.getRumId()).orElse(null);

        return new Bokning(
                bokningDTO.getId(),
                bokningDTO.getNätter(),
                bokningDTO.getStartSlutDatum(),
                kund,
                rum,
                bokningDTO.isAvbokad()
        );
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

    public BokningDTO addBokningFromDTO(BokningDTO bokningDTO) {
        Bokning bokning = new Bokning(
                bokningDTO.getId(),
                bokningDTO.getNätter(),
                bokningDTO.getStartSlutDatum(),
                null,
                null,
                false
        );

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

    public BokningDTO updateBokning(Long id, BokningDTO updatedBokningDTO) {
        Bokning updatedBokning = convertToEntity(updatedBokningDTO);
        Optional<Bokning> optionalBokning = bokningRepo.findById(id);
        if (optionalBokning.isPresent()) {
            Bokning existingBokning = optionalBokning.get();
            Bokning savedBokning = updateBokningDetails(existingBokning, updatedBokning);
            return convertToBokningDTO(savedBokning);
        } else {
            throw new RuntimeException("Bokning not found with id: " + id);
        }
    }

    public BokningDTO updateBokningFromEntity(Long id, Bokning updatedBokning) {
        Optional<Bokning> optionalBokning = bokningRepo.findById(id);
        if (optionalBokning.isPresent()) {
            Bokning existingBokning = optionalBokning.get();
            Bokning savedBokning = updateBokningDetails(existingBokning, updatedBokning);
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

    public List<Rum> searchAvailableRooms(String startDate, String endDate, int nätter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        List<Rum> allRooms = rumRepo.findAll();
        List<Rum> availableRooms = new ArrayList<>();

        for (Rum rum : allRooms) {
            if (isRoomAvailable(rum, startDate + "-" + endDate)) {
                availableRooms.add(rum);
            }
        }

        return availableRooms;
    }
}