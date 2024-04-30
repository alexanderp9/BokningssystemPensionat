package com.example.pensionatdb.dtos;

import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.models.Rum;
import lombok.*;

import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedBokningDto extends BokningDTO {
    private kundDto kund;

    private RumDTO rum;
}