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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class BokningService {

    private static final Logger log = Logger.getLogger(BokningService.class.getName());

    private final bokningRepo bokningRepo;
    private final kundRepo kundRepo;
    private final rumRepo rumRepo;
    private final DiscountService discountService;

    @Autowired
    public BokningService(bokningRepo bokningRepo, kundRepo kundRepo, rumRepo rumRepo, DiscountService discountService) {
        this.bokningRepo = bokningRepo;
        this.kundRepo = kundRepo;
        this.rumRepo = rumRepo;
        this.discountService = discountService;
    }

    public List<Bokning> findBokningarByKund(Kund kund) {
        return bokningRepo.findByKund(kund);
    }

    private BokningDTO convertToBokningDTO(Bokning bokning) {
        BokningDTO dto = new BokningDTO();
        dto.setId(bokning.getId());
        dto.setNätter(bokning.getNätter());
        dto.setStartDatum(bokning.getStartDatum());
        dto.setSlutDatum(bokning.getSlutDatum());
        dto.setNamn(bokning.getKund().getNamn());
        dto.setKundId(bokning.getKund().getId());
        dto.setRumId(bokning.getRum().getId());
        dto.setEmail(bokning.getKund().getEmail());

        return dto;
    }

    public Bokning convertToEntity(BokningDTO bokningDTO) {
        Kund kund = kundRepo.findById(bokningDTO.getKundId()).orElse(null);
        Rum rum = rumRepo.findById(bokningDTO.getRumId()).orElse(null);

        return new Bokning(
                bokningDTO.getId(),
                bokningDTO.getNätter(),
                bokningDTO.getStartDatum(),
                bokningDTO.getSlutDatum(),
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

    public BokningDTO addBokningFromDTO(BokningDTO bokningDTO) {
        Rum rum = rumRepo.findById(bokningDTO.getRumId()).orElse(null);
        if (rum == null) {
            return null;
        }

        if (!isRoomAvailable(rum, bokningDTO.getStartDatum(), bokningDTO.getSlutDatum())) {
            return null;
        }

        Kund kund = kundRepo.findById(bokningDTO.getKundId()).orElse(null);
        if (kund == null) {
            return null;
        }

        double roomPrice = rum.getRumTyp().equalsIgnoreCase("Enkelrum") ? 100.0 : 200.0;
        double discountedPrice = discountService.discountBokning(kund, bokningDTO.getNätter(), bokningDTO.getStartDatum(), bokningDTO.getSlutDatum(), roomPrice);

        Bokning bokning = new Bokning(
                bokningDTO.getId(),
                bokningDTO.getNätter(),
                bokningDTO.getStartDatum(),
                bokningDTO.getSlutDatum(),
                kund,
                rum,
                bokningDTO.isAvbokad()
        );

        log.info("Totalpris med rabatt: " + discountedPrice);

        Bokning savedBokning = bokningRepo.save(bokning);
        return convertToBokningDTO(savedBokning);
    }

    public void deleteBokning(Long id) {
        bokningRepo.deleteById(id);
    }

    public boolean isRoomAvailable(Rum rum, LocalDate startDatum, LocalDate slutDatum) {
        List<Bokning> existingBookings = bokningRepo.findByRumAndStartDatumLessThanEqualAndSlutDatumGreaterThanEqual(rum, slutDatum, startDatum);

        for (Bokning booking : existingBookings) {
            if (startDatum.isBefore(booking.getSlutDatum()) && slutDatum.isAfter(booking.getStartDatum())) {
                return false;
            }
        }
        return true;
    }

    public List<Rum> searchAvailableRoomsByDateRange(LocalDate startDatum, LocalDate slutDatum) {
        List<Rum> allRooms = rumRepo.findAll();
        List<Rum> availableRooms = new ArrayList<>();

        for (Rum rum : allRooms) {
            if (isRoomAvailable(rum, startDatum, slutDatum)) {
                availableRooms.add(rum);
            }
        }
        return availableRooms;
    }
}
