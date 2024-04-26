package com.example.pensionatdb.dtos;

import lombok.Data;

@Data
public class BokningDTO {
    private long id;
    private int nätter;
    private String startSlutDatum;
    private String namn;
    private long kundId;
    private long rumId;
    private boolean avbokad;
}