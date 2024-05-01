package com.example.pensionatdb.dtos;



import lombok.*;



@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedBokningDto extends BokningDTO {
    private kundDto kund;

    private RumDTO rum;
}