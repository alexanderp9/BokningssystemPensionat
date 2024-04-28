package com.example.pensionatdb.models;

import jakarta.persistence.*;
import lombok.*;

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

    @Getter
    private int capacity;

    public Rum(String rumTyp, int extraSäng, int capacity) {
        this.rumTyp = rumTyp;
        this.extraSäng = extraSäng;
        this.capacity = capacity;
    }

}
