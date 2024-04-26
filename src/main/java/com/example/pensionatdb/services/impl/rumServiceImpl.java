package com.example.pensionatdb.services.impl;

import com.example.pensionatdb.dtos.DetailedRumDto;
import com.example.pensionatdb.dtos.rumDto;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.rumRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class rumServiceImpl  {

    final private rumRepo rr;


    public List<DetailedRumDto> getAllRum() {
        return rr.findAll().stream().map(k-> rumToDetDetailedRumDto(k)).toList();
    }


    public rumDto rumTorumDto(Rum r) {
        return rumDto.builder()
                .id(r.getId())
                .rumTyp(r.getRumTyp())
                .build();
    }


    public DetailedRumDto rumToDetDetailedRumDto(Rum r) {
        return DetailedRumDto.builder()
                .id(r.getId())
                .rumTyp(r.getRumTyp())
                .extraSäng(r.getExtraSäng())
                .build();
    }


}
