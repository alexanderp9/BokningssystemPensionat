package com.example.pensionatdb.services.impl;


import com.example.pensionatdb.dtos.DetailedKundDto;
import com.example.pensionatdb.dtos.kundDto;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.repos.kundRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class kundServiceImpl  {

    final private kundRepo kr;



    public List<DetailedKundDto> getAllKnud() {
        return kr.findAll().stream().map(k-> kundToDetDetailedKundDto(k)).toList();
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
