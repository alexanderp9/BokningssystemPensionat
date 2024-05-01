package com.example.pensionatdb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Kund {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Name is Mandatory")
    @Size(min = 3, message ="At least 3 Letters ")
    @Pattern(regexp="^[A-Öa-ö]*$", message = "Only Letter for Name")
    private String namn;

    private String adress;


}
