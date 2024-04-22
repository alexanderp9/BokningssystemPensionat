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
public class Rum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "rumTyp")
    private String rumTyp;

    private int extraSäng;

    public Rum(String rumTyp, int extraSäng) {
        this.rumTyp = rumTyp;
        this.extraSäng = extraSäng;
    }
}
