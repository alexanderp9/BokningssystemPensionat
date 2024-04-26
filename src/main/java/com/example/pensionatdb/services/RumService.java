package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.RumDTO;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.rumRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RumService {

    private final rumRepo rumRepo;
    private static final Logger log = LoggerFactory.getLogger(RumService.class);

    @Autowired
    public RumService(rumRepo rumRepo) {
        this.rumRepo = rumRepo;
    }



    public List<RumDTO> getAllRumDTOs() {
        List<Rum> rumList = rumRepo.findAll();
        return rumList.stream()
                .map(this::convertToRumDTO)
                .collect(Collectors.toList());
    }


    public RumDTO addRumFromDTO(RumDTO rumDTO) {
        Rum rum = convertToRum(rumDTO);
        Rum savedRum = rumRepo.save(rum);
        log.info("Rum added: " + savedRum.toString());
        return convertToRumDTO(savedRum);
    }

    public void deleteRumById(Long id) {
        rumRepo.deleteById(id);
        log.info("Rum deleted with id " + id);
    }

    public RumDTO findRumDTOById(Long id) {
        Optional<Rum> rumOptional = rumRepo.findById(id);
        RumDTO rumDTO = rumOptional.map(this::convertToRumDTO)
                .orElseThrow(() -> new RuntimeException("RumDTO not found"));
        return rumDTO;
    }

    private RumDTO convertToRumDTO(Rum rum) {
        RumDTO rumDTO = new RumDTO();
        rumDTO.setId(rum.getId());
        rumDTO.setRumTyp(rum.getRumTyp());
        rumDTO.setExtraSäng(rum.getExtraSäng());
        return rumDTO;
    }

    private Rum convertToRum(RumDTO rumDTO) {
        Rum rum = new Rum();
        rum.setId(rumDTO.getId());
        rum.setRumTyp(rumDTO.getRumTyp());
        rum.setExtraSäng(rumDTO.getExtraSäng());
        return rum;
    }
}
