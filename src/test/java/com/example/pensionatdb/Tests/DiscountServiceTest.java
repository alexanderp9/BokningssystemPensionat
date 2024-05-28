package com.example.pensionatdb.Tests;

import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.repos.bokningRepo;
import com.example.pensionatdb.services.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DiscountServiceTest {

    private bokningRepo bokningRepo;
    private DiscountService discountService;

    @BeforeEach
    public void setUp() {
        bokningRepo = Mockito.mock(bokningRepo.class);
        discountService = new DiscountService(bokningRepo);
    }

    @Test
    public void testCalculateDiscount_WithMoreThanTwoNights() {
        Kund kund = new Kund();
        LocalDate startDate = LocalDate.of(2024, 5, 29);
        LocalDate endDate = LocalDate.of(2024, 6, 1);
        double roomPrice = 100.0;
        int nätter = 3;

        double result = discountService.discountBokning(kund, nätter, startDate, endDate, roomPrice);

        double expectedPrice = roomPrice * nätter * (1 - 0.005);
        assertEquals(expectedPrice, result);
    }

    @Test
    public void testCalculateDiscount_WithSundayToMonday() {
        Kund kund = new Kund();
        LocalDate startDate = LocalDate.of(2024, 6, 9);
        LocalDate endDate = LocalDate.of(2024, 6, 10);
        double roomPrice = 100.0;
        int nätter = 1;

        double result = discountService.discountBokning(kund, nätter, startDate, endDate, roomPrice);

        double expectedPrice = roomPrice * nätter * (1 - 0.02);
        assertEquals(expectedPrice, result);
    }

    @Test
    public void testCalculateDiscount_WithMoreThanTenNightsInYear() {
        Kund kund = new Kund();
        LocalDate startDate = LocalDate.of(2024, 5, 29);
        LocalDate endDate = LocalDate.of(2024, 6, 1);
        double roomPrice = 100.0;
        int nätter = 3;

        Bokning bokning = new Bokning();
        bokning.setNätter(10);
        when(bokningRepo.findByKundAndStartDatumAfter(eq(kund), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(bokning));

        double result = discountService.discountBokning(kund, nätter, startDate, endDate, roomPrice);

        double expectedDiscount = 0.02 + 0.005;
        double expectedPrice = roomPrice * nätter * (1 - expectedDiscount);
        assertEquals(expectedPrice, result);
    }

    @Test
    public void testCalculateDiscount_WithAllDiscounts() {
        Kund kund = new Kund();
        LocalDate startDate = LocalDate.of(2024, 5, 31);
        LocalDate endDate = LocalDate.of(2024, 6, 4);
        double roomPrice = 100.0;
        int nätter = 4;

        Bokning bokning = new Bokning();
        bokning.setNätter(10);
        when(bokningRepo.findByKundAndStartDatumAfter(eq(kund), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(bokning));

        double result = discountService.discountBokning(kund, nätter, startDate, endDate, roomPrice);

        double expectedPrice = roomPrice * nätter * (1 - 0.045);
        assertEquals(expectedPrice, result);
    }

    @Test
    public void testCalculateDiscount_NoDiscounts() {
        Kund kund = new Kund();
        LocalDate startDate = LocalDate.of(2024, 6, 5);
        LocalDate endDate = LocalDate.of(2024, 6, 6);
        double roomPrice = 100.0;
        int nätter = 1;

        when(bokningRepo.findByKundAndStartDatumAfter(eq(kund), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        double result = discountService.discountBokning(kund, nätter, startDate, endDate, roomPrice);

        double expectedPrice = roomPrice * nätter;
        assertEquals(expectedPrice, result);
    }
}