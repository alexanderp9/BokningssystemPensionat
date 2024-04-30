package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.RumDTO;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.rumRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RumService {

    private final rumRepo rumRepository;
    private final BokningService bokningService;


    public List<RumDTO> searchAvailableRooms(LocalDate startDate, LocalDate endDate, int numberOfPersons) {
        List<RumDTO> availableRooms = new ArrayList<>();
        List<Rum> allRooms = rumRepository.findAll();

        for (Rum rum : allRooms) {
            if (isRoomAvailable(rum, startDate, endDate) && roomCanAccommodate(rum, numberOfPersons)) {
                RumDTO rumDTO = convertToRumDTO(rum);
                availableRooms.add(rumDTO);
            }
        }

        return availableRooms;
    }

    public boolean isRoomAvailable(Rum rum, LocalDate startDate, LocalDate endDate) {
        String startEndDate = startDate.format(DateTimeFormatter.ofPattern("yyMMdd")) + "-" +
                endDate.format(DateTimeFormatter.ofPattern("yyMMdd"));
        return bokningService.isRoomAvailable(rum, startEndDate);
    }

    private boolean roomCanAccommodate(Rum rum, int numberOfPersons) {
        int roomCapacity = rum.getExtraSäng() > 0 ? 2 : 1; // Om rummet har extra sängar, räkna det som ett dubbelrum
        return roomCapacity >= numberOfPersons;
    }

    public RumDTO convertToRumDTO(Rum rum) {
        RumDTO rumDTO = new RumDTO();
        rumDTO.setId(rum.getId());
        rumDTO.setRumTyp(rum.getRumTyp());
        rumDTO.setExtraSäng(rum.getExtraSäng());
        return rumDTO;
    }
}
