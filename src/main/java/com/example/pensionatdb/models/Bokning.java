package com.example.pensionatdb.models;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Bokning {

    @Id
    @GeneratedValue
    protected long id;

    int nätter;

    int startSlutDatum;


    @ManyToOne
    @JoinColumn
    private Kund kund;

    @ManyToOne
    @JoinColumn
    private Rum rum;


    public Bokning(int nätter, int startSlutDatum, Kund kund, Rum rum) {
        this.nätter = nätter;
        this.startSlutDatum = startSlutDatum;
        this.kund = kund;
        this.rum = rum;
    }
}
