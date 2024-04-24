package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.DetailedKundDto;
import com.example.pensionatdb.dtos.kundDto;
import com.example.pensionatdb.models.Kund;

import java.util.List;

public interface kundService {

    public kundDto kundTokundDto(Kund k);
    public DetailedKundDto kundToDetDetailedKundDto(Kund k);

    public List<DetailedKundDto> getAllKnud();
}
