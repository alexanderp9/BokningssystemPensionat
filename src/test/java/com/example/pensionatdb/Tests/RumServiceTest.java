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
@SpringBootTest
public class RumServiceTest {


    @Mock
    private rumRepo rr;
    @Mock
    private BokningService br;

    @InjectMocks
    private RumService service = new RumService(rr, br);



    Kund kund = new Kund(1L, "Anders", "solgatan2", "hej@hotmail.com");

    long id = 1L;

    String rumTyp = "enkelRum";

    int extraSäng = 1;

    Rum rum = new Rum(id, rumTyp, extraSäng);


    Bokning bokning = new Bokning(1L, 3, "240510-240515", kund, rum, false);


    RumDTO rumDTO = RumDTO.builder()
            .id(id)
            .rumTyp(rumTyp)
            .extraSäng(extraSäng)
            .build();


    @Test
    void testIsRoomAvailableWhenRoomIsNotAvailable(){
        LocalDate startDate = LocalDate.of(2024, 5, 10);
        LocalDate endDate = LocalDate.of(2024, 5, 15);
        RumService service1 = new RumService(rr, br);

        when(br.isRoomAvailable(rum, "240510-240515")).thenReturn(false);
        boolean isAvailable = service1.isRoomAvailable(rum, startDate, endDate);


        assertFalse(isAvailable);
    }

    @Test
    void testIsRoomAvailableWhenRoomIsAvailable(){
        LocalDate startDate = LocalDate.of(2024, 5, 20);
        LocalDate endDate = LocalDate.of(2024, 5, 25);
        RumService service2 = new RumService(rr, br);

        when(br.isRoomAvailable(rum, "240520-240525")).thenReturn(true);
        boolean isAvailable = service2.isRoomAvailable(rum, startDate, endDate);


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
