package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.DetailedKundDto;
import com.example.pensionatdb.dtos.DetailedRumDto;
import com.example.pensionatdb.dtos.kundDto;
import com.example.pensionatdb.dtos.rumDto;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.models.Rum;

import java.util.List;

public interface rumService {

    public rumDto rumTorumDto(Rum r);
    public DetailedRumDto rumToDetDetailedRumDto(Rum r);

    public List<DetailedRumDto> getAllRum();
}
