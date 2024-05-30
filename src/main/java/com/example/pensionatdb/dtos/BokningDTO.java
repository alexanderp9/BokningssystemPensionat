package com.example.pensionatdb.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BokningDTO {
    private Long id;
    private int nätter;
    private LocalDate startDatum;
    private LocalDate slutDatum;
    private String namn;
    private long kundId;
    private long rumId;
    private boolean avbokad;
    private String email;
    private double totalPrice;

    public double calculateTotalPrice(double pricePerNight) {
        return nätter*pricePerNight;
    }

}

