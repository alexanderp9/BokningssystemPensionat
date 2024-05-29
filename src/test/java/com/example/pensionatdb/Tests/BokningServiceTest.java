package com.example.pensionatdb.Tests;
import com.example.pensionatdb.dtos.BokningDTO;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;

import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.bokningRepo;
import com.example.pensionatdb.repos.kundRepo;
import com.example.pensionatdb.repos.rumRepo;
import com.example.pensionatdb.services.BokningService;
import com.example.pensionatdb.services.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BokningServiceTest {

    @Mock
    private bokningRepo mockBokningRepo;

    @Mock
    private kundRepo mockKundRepo;

    @Mock
    private rumRepo mockRumRepo;

    @Mock
    private DiscountService mockDiscountService;  // Add this line

    @InjectMocks
    private BokningService bokningService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testFindBokningarByKund() {
        Kund kund = new Kund();
        when(mockBokningRepo.findByKund(kund)).thenReturn(Arrays.asList(new Bokning(), new Bokning()));

        List<Bokning> result = bokningService.findBokningarByKund(kund);

        assertEquals(2, result.size());
        verify(mockBokningRepo, times(1)).findByKund(kund);
    }
    @Test
    public void testConvertToEntity() {

        BokningDTO bokningDTO = new BokningDTO();
        bokningDTO.setId(1L);
        bokningDTO.setNätter(3);
        bokningDTO.setStartDatum(LocalDate.parse("2024-01-01"));
        bokningDTO.setSlutDatum(LocalDate.parse("2024-01-04"));
        bokningDTO.setKundId(1L);
        bokningDTO.setRumId(1L);
        bokningDTO.setAvbokad(false);


        Kund mockKund = new Kund();
        mockKund.setId(1L);
        when(mockKundRepo.findById(1L)).thenReturn(Optional.of(mockKund));

        Rum mockRum = new Rum();
        mockRum.setId(1L);
        when(mockRumRepo.findById(1L)).thenReturn(Optional.of(mockRum));

   Bokning result = bokningService.convertToEntity(bokningDTO);


       assertEquals(bokningDTO.getId(), result.getId());
       assertEquals(bokningDTO.getNätter(), result.getNätter());
       assertEquals(bokningDTO.getStartDatum(), result.getStartDatum());
       assertEquals(bokningDTO.getSlutDatum(), result.getSlutDatum());
       assertEquals(mockKund, result.getKund());
       assertEquals(mockRum, result.getRum());
       assertEquals(bokningDTO.isAvbokad(), result.isAvbokad());
    }
    @Test
    public void testGetAllBokningDTOs() {

        List<Bokning> bokningar = new ArrayList<>();


        when(mockBokningRepo.findAll()).thenReturn(bokningar);


        List<BokningDTO> result = bokningService.getAllBokningDTOs();


        assertEquals(bokningar.size(), result.size());

    }

    @Test
    public void testDeleteBokning() {

        long bokningId = 1L;


        doNothing().when(mockBokningRepo).deleteById(bokningId);


        bokningService.deleteBokning(bokningId);


        verify(mockBokningRepo, times(1)).deleteById(bokningId);
    }

    @Test
    public void testAddBokningFromDTO() {
        BokningDTO bokningDTO = new BokningDTO();
        bokningDTO.setId(1L);
        bokningDTO.setNätter(3);
        bokningDTO.setStartDatum(LocalDate.parse("2024-01-01"));
        bokningDTO.setSlutDatum(LocalDate.parse("2024-01-04"));
        bokningDTO.setKundId(1L);
        bokningDTO.setRumId(1L);
        bokningDTO.setAvbokad(false);

        Kund mockKund = new Kund();
        mockKund.setId(1L);
        when(mockKundRepo.findById(1L)).thenReturn(Optional.of(mockKund));

        Rum mockRum = new Rum();
        mockRum.setId(1L);
        mockRum.setRumTyp("Single");
        when(mockRumRepo.findById(1L)).thenReturn(Optional.of(mockRum));

        double roomPrice = 100.0;
        double discountedPrice = 90.0;
        when(mockDiscountService.discountBokning(any(Kund.class), anyInt(), any(LocalDate.class), any(LocalDate.class), anyDouble()))
                .thenReturn(discountedPrice);

        Bokning expectedBokning = new Bokning();
        expectedBokning.setId(1L);
        expectedBokning.setNätter(3);
        expectedBokning.setStartDatum(LocalDate.parse("2024-01-01"));
        expectedBokning.setSlutDatum(LocalDate.parse("2024-01-04"));
        expectedBokning.setKund(mockKund);
        expectedBokning.setRum(mockRum);
        expectedBokning.setAvbokad(false);

        when(mockBokningRepo.save(any(Bokning.class))).thenReturn(expectedBokning);

        BokningDTO result = bokningService.addBokningFromDTO(bokningDTO);

        assertNotNull(result);
        assertEquals(expectedBokning.getId(), result.getId());
        assertEquals(expectedBokning.getNätter(), result.getNätter());
        assertEquals(bokningDTO.getStartDatum(), result.getStartDatum());
        assertEquals(bokningDTO.getSlutDatum(), result.getSlutDatum());
        assertEquals(expectedBokning.getKund().getId(), result.getKundId());
        assertEquals(expectedBokning.getRum().getId(), result.getRumId());
        assertEquals(expectedBokning.isAvbokad(), result.isAvbokad());
        verify(mockBokningRepo, times(1)).save(any(Bokning.class));
        verify(mockDiscountService, times(1)).discountBokning(any(Kund.class), anyInt(), any(LocalDate.class), any(LocalDate.class), anyDouble());
    }


}