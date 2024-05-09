package com.example.pensionatdb.dtos;

import lombok.Builder;
import lombok.Data;

@Data
public class BokningDTO {
    private Long id;
    private int nätter;
    private String startSlutDatum;
    private String namn;
    private long kundId;
    private long rumId;
    private String email;
    private boolean avbokad;
}