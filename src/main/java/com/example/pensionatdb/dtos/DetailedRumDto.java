package com.example.pensionatdb.dtos;

import com.example.pensionatdb.models.Rum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedRumDto extends Rum {

    private Long id;

    private String rumTyp;

    private int extraSäng;
}
