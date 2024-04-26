package com.example.pensionatdb.services.impl;


import com.example.pensionatdb.dtos.DetailedKundDto;
import com.example.pensionatdb.dtos.kundDto;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.repos.bokningRepo;
import com.example.pensionatdb.repos.kundRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class kundServiceImpl  {

    final private kundRepo kr;


    public List<DetailedKundDto> getAllKund() {
        return kr.findAll().stream().map(k-> kundToDetDetailedKundDto(k)).toList();
    }


    public Optional<Kund> findById(Long id) {
        return kr.findById(id);
    }

    public void deleteById(Long id){
        if (kr.existsById(id)) {
            kr.deleteById(id);
        }
    }


    public kundDto kundTokundDto(Kund k) {
        return kundDto.builder()
                .id(k.getId())
                .namn(k.getNamn())
                .build();
    }



    public DetailedKundDto kundToDetDetailedKundDto(Kund k) {
        return DetailedKundDto.builder()
                .id(k.getId())
                .namn(k.getNamn())
                .adress(k.getAdress())
                .build();
    }


}
