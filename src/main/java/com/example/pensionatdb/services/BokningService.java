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


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.sql.Date;

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

    public List<Bokning> findBokningarByKund(Kund kund) {
        return bokningRepo.findByKund(kund);
    }


    private BokningDTO convertToBokningDTO(Bokning bokning) {
        BokningDTO dto = new BokningDTO();
        dto.setId(bokning.getId());
        dto.setStart(bokning.getStart());
        dto.setEnd(bokning.getEnd());



        if (bokning.getKund() != null) {
            dto.setNamn(bokning.getKund().getNamn());
            dto.setKundId(bokning.getKund().getId());
        }


        if (bokning.getRum() != null) {
            dto.setRumId(bokning.getRum().getId());
        }

        dto.setAvbokad(bokning.isAvbokad());

        return dto;
    }

    public Bokning convertToEntity(BokningDTO bokningDTO) {
        Kund kund = kundRepo.findById(bokningDTO.getKundId()).orElse(null);
        Rum rum = rumRepo.findById(bokningDTO.getRumId()).orElse(null);

        return new Bokning(
                bokningDTO.getId(),
                bokningDTO.getNätter(),
                bokningDTO.getStart(),
                bokningDTO.getEnd(),
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



    public BokningDTO addBokning(BokningDTO bokningDTO) {

        Kund kund = kundRepo.findById(bokningDTO.getKundId()).orElse(null);

        Rum rum = rumRepo.findById(bokningDTO.getRumId()).orElse(null);
        System.out.println(bokningDTO.toString());
        Bokning bokning = new Bokning(
                bokningDTO.getId(),
                bokningDTO.getNätter(),
                bokningDTO.getStart(),
                bokningDTO.getEnd(),
                kund,
                rum,
                bokningDTO.isAvbokad()
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


    public boolean isRoomAvailable(Rum rum, Date startDate, Date endDate) {
        List<Bokning> existingBookings = bokningRepo.findByRumAndStartLessThanEqualAndEndGreaterThanEqual(rum, endDate, startDate);
        return existingBookings.isEmpty();
    }

    public List<Rum> searchAvailableRooms(Date startDate, Date endDate) {
        List<Rum> allRooms = rumRepo.findAll();
        List<Rum> availableRooms = new ArrayList<>();

        for (Rum rum : allRooms) {
            if (isRoomAvailable(rum, startDate, endDate)) {
                availableRooms.add(rum);
            }
        }

        return availableRooms;
    }
}