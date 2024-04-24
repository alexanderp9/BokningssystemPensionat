package com.example.pensionatdb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity

public class Kund {

    @Id
    @GeneratedValue
    protected Long id;

    @NotEmpty(message = "Name is Mandatory")
    @Size(min = 3, message ="At least 3 Letters for Author")
    @Pattern(regexp="^[A-Öa-ö]*$", message = "Only Letter for Author")
    String namn;

    String adress;

    public Kund(String namn, String adress) {
        this.namn = namn;
        this.adress = adress;
    }
}
