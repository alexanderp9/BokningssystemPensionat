package com.example.pensionatdb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Rum {

    @Id
    @GeneratedValue
    protected Long id;

    String rumTyp;

    int extraSäng;

    public Rum(String rumTyp, int extraSäng) {
        this.rumTyp = rumTyp;
        this.extraSäng = extraSäng;
    }
}
