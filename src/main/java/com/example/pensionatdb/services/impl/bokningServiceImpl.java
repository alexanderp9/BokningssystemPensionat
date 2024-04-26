package com.example.pensionatdb.services.impl;


import com.example.pensionatdb.dtos.*;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.bokningRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class bokningServiceImpl{

    final private bokningRepo br;



    public List<DetailedBokningDto> getAllBokning() {
        return br.findAll().stream().map(k-> bokningToDetailedBokningDto(k)).toList();
    }



    public bokningDto bokningTobokningDto(Bokning b) {
        return bokningDto.builder()
                .id(b.getId())
                .startSlutDatum(b.getStartSlutDatum())
                .build();
    }


    public DetailedBokningDto bokningToDetailedBokningDto(Bokning b) {
        return DetailedBokningDto.builder()
                .id(b.getId())
                .nätter(b.getNätter())
                .startSlutDatum(b.getStartSlutDatum())
                .kund(new DetailedKundDto
                        (b.getKund().getId(),
                                b.getKund().getNamn(),
                                b.getKund().getAdress()))
                .rum(new DetailedRumDto
                        (b.getRum().getId(),
                                b.getRum().getRumTyp(),
                                b.getRum().getExtraSäng()))
                .build();
    }


}
