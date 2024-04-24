package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.DetailedBokningDto;
import com.example.pensionatdb.dtos.DetailedKundDto;
import com.example.pensionatdb.dtos.bokningDto;
import com.example.pensionatdb.models.Bokning;

import java.util.List;

public interface bokningService {


    public bokningDto bokningTobokningDto(Bokning b);

    public DetailedBokningDto bokningToDetailedBokningDto(Bokning b);

    public List<DetailedBokningDto> getAllBokning();
}
