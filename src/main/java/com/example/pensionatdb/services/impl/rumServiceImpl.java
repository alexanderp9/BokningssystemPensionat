package com.example.pensionatdb.services.impl;

import com.example.pensionatdb.dtos.DetailedRumDto;
import com.example.pensionatdb.dtos.rumDto;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.rumRepo;
import com.example.pensionatdb.services.rumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class rumServiceImpl implements rumService {

    final private rumRepo rr;

    @Override
    public List<DetailedRumDto> getAllRum() {
        return rr.findAll().stream().map(k-> rumToDetDetailedRumDto(k)).toList();
    }

    @Override
    public rumDto rumTorumDto(Rum r) {
        return rumDto.builder()
                .id(r.getId())
                .rumTyp(r.getRumTyp())
                .build();
    }

    @Override
    public DetailedRumDto rumToDetDetailedRumDto(Rum r) {
        return DetailedRumDto.builder()
                .id(r.getId())
                .rumTyp(r.getRumTyp())
                .extraSäng(r.getExtraSäng())
                .build();
    }


}
