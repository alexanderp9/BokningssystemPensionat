package com.example.pensionatdb.services.impl;


import com.example.pensionatdb.dtos.*;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.bokningRepo;
import com.example.pensionatdb.services.bokningService;
import com.example.pensionatdb.services.kundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class bokningServiceImpl implements bokningService {

    final private bokningRepo br;


    @Override
    public List<DetailedBokningDto> getAllBokning() {
        return br.findAll().stream().map(k-> bokningToDetailedBokningDto(k)).toList();
    }


    @Override
    public bokningDto bokningTobokningDto(Bokning b) {
        return bokningDto.builder()
                .id(b.getId())
                .startSlutDatum(b.getStartSlutDatum())
                .build();
    }

    @Override
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
