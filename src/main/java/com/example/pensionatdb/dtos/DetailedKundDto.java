package com.example.pensionatdb.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedKundDto {

    private Long id;

    private String namn;

    private String adress;
    private String namn;
}