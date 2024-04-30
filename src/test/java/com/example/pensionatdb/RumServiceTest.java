package com.example.pensionatdb;

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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RumServiceTest {


    @Mock
    private rumRepo rr;
    @Mock
    private BokningService br;

    @InjectMocks
    private RumService service = new RumService(rr, br);



    Kund kund = new Kund(1L, "Anders", "solgatan2");

    long id = 1L;

    String rumTyp = "enkelRum";

    int extraSäng = 0;

    Rum rum = new Rum(id, rumTyp, extraSäng);


    Bokning bokning = new Bokning(1L, 3, "120524-150524", kund, rum, false);


    RumDTO rumDTO = RumDTO.builder()
            .id(id)
            .rumTyp(rumTyp)
            .extraSäng(extraSäng)
            .build();

    @Test
    void rumDTO(){

        RumDTO actual = serv

    }
}
