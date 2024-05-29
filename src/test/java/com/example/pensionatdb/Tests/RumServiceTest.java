package com.example.pensionatdb.Tests;

import com.example.pensionatdb.dtos.BokningDTO;
import com.example.pensionatdb.dtos.RumDTO;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.rumRepo;
import com.example.pensionatdb.services.BokningService;
import com.example.pensionatdb.services.RumService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RumServiceTest {


    @Mock
    private BokningService br;

    @InjectMocks
    private RumService service;

    private final long id = 1L;
    private final String rumTyp = "enkelRum";
    private final int extraSäng = 1;
    private final Rum rum = new Rum(id, rumTyp, 100, extraSäng);

    RumDTO rumDTO = RumDTO.builder()
            .id(id)
            .rumTyp(rumTyp)
            .extraSäng(extraSäng)
            .build();


    @Test
    void testIsRoomAvailableWhenRoomIsNotAvailable() {
        LocalDate startDate = LocalDate.of(2024, 5, 10);
        LocalDate endDate = LocalDate.of(2024, 5, 15);

        when(br.isRoomAvailable(rum, startDate, endDate)).thenReturn(false);
        boolean isAvailable = service.isRoomAvailable(rum, startDate, endDate);

        assertFalse(isAvailable);
    }

    @Test
    void testIsRoomAvailableWhenRoomIsAvailable() {
        LocalDate startDate = LocalDate.of(2024, 5, 20);
        LocalDate endDate = LocalDate.of(2024, 5, 25);

        when(br.isRoomAvailable(rum, startDate, endDate)).thenReturn(true);
        boolean isAvailable = service.isRoomAvailable(rum, startDate, endDate);

        assertTrue(isAvailable);
    }

    @Test
    void rumDTO(){

        RumDTO actual = service.convertToRumDTO(rum);
        assertEquals(actual.getId(), rumDTO.getId());
        assertEquals(actual.getRumTyp(), rumDTO.getRumTyp());
        assertEquals(actual.getExtraSäng(), rumDTO.getExtraSäng());
    }
}