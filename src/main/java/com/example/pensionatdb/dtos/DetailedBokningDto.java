package com.example.pensionatdb.dtos;

import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.models.Rum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedBokningDto {

    private Long id;

    private int nätter;

    private int startSlutDatum;

    private DetailedKundDto kund;

    private DetailedRumDto rum;
}
