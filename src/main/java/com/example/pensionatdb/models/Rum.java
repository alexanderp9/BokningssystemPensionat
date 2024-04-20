package com.example.pensionatdb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Rum {

    @Id
    @GeneratedValue
    protected long id;

    String rumTyp;

    int extraSäng;

    public Rum(String rumTyp, int extraSäng) {
        this.rumTyp = rumTyp;
        this.extraSäng = extraSäng;
    }
}
