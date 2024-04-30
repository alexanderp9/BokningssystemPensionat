package com.example.pensionatdb.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BokningDTO {
    private long id;
    private int nätter;
    private String startSlutDatum;
    private String kundNamn;
    private long rumId;
}
