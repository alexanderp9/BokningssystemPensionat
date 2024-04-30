package com.example.pensionatdb.models;


import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity

public class Bokning {

    @Id
    @GeneratedValue
    protected Long id;

    int nätter;

    String startSlutDatum;


    @ManyToOne
    @JoinColumn(name = "kund_id")
    private Kund kund;

    @ManyToOne
    @JoinColumn(name = "rum_id")
    private Rum rum;


    public Bokning(int nätter, String startSlutDatum, Kund kund, Rum rum) {
        this.nätter = nätter;
        this.startSlutDatum = startSlutDatum;
        this.kund = kund;
        this.rum = rum;
    }
}
