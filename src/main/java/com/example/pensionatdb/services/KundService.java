package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.DetailedKundDto;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.repos.kundRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class KundService  {

    private final kundRepo kr;


    public void updateKundAddressById(Long id, String newAddress) {
        kr.updateAddressById(id, newAddress);
    }

    public void updateKundNameById(Long id, String newName) {
        kr.updateNameById(id, newName);
    }

    public List<DetailedKundDto> getAllKund() {
        return kr.findAll().stream().map(this::kundToDetDetailedKundDto).toList();
    }

    public String save(Kund k){
        kr.save(k);
        return "Konto sparat";
    }

    public Optional<Kund> findById(Long id) {
        return kr.findById(id);
    }

    public void deleteById(Long id){
        if (kr.existsById(id)) {
            kr.deleteById(id);
        }
    }


    public DetailedKundDto kundToDetDetailedKundDto(Kund k) {
        return DetailedKundDto.builder()
                .id(k.getId())
                .namn(k.getNamn())
                .adress(k.getAdress())
                .email(k.getEmail())
                .build();
    }


}