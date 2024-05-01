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
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Kund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Name is Mandatory")
    @Size(min = 3, message ="At least 3 Letters ")
    @Pattern(regexp="^[A-Öa-ö]*$", message = "Only Letter for Name")
    @NotNull
    private String namn;
    @NotNull
    private String adress;


}
