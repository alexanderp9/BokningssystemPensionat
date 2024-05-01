package com.example.pensionatdb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Kund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;


    @NotEmpty(message = "Name is Mandatory")
    @Size(min = 3, message ="At least 3 Letters ")
    @Pattern(regexp="^[A-Öa-ö]*$", message = "Only Letter for Name")
    @NotNull
    private String namn;
    @NotNull
    private String adress;

    public Kund(String namn, String adress) {
        this.namn = namn;
        this.adress = adress;
    }
}
