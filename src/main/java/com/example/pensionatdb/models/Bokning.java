package com.example.pensionatdb.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Bokning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int nätter;
    private Date start;
    private Date end;

    @ManyToOne
    @JoinColumn(name = "kund_id")
    private Kund kund;

    @ManyToOne
    @JoinColumn(name = "rum_id")
    private Rum rum;

    private boolean avbokad = false;
}