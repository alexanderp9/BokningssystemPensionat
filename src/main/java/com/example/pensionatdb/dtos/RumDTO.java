package com.example.pensionatdb.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RumDTO {

    private long id;
    private String rumTyp;
    private int extraSäng;
}