package com.example.pensionatdb.Tests;


import com.example.pensionatdb.dtos.DetailedKundDto;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.repos.kundRepo;
import com.example.pensionatdb.services.KundService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class KundServiceTest {

    @Mock
    private kundRepo kr;

    @InjectMocks
    private KundService service = new KundService(kr);


    long id = 1L;

    String namn = "Anders";

    String adress = "solgatan2";

    String email = "hejhej";


    Kund kund = new Kund(1L, namn, adress, email);

    DetailedKundDto detailedKundDto = DetailedKundDto.builder()
            .id(id)
            .namn(namn)
            .adress(adress)
            .build();

    @Test
    void getAllKonton(){
        when(kr.findAll()).thenReturn(Arrays.asList(kund));
        KundService service1 = new KundService(kr);
        List<DetailedKundDto> allKonton = service1.getAllKund();

        assertEquals(1, allKonton.size());
    }

    @Test
    void save(){
        when(kr.save(kund)).thenReturn(kund);
        KundService service1 = new KundService(kr);
        String feedback = service1.save(kund);
        assertEquals("Konto sparat", feedback);
    }


    @Test
    void updateKundName(){
        KundService service1 = new KundService(kr);
        service1.updateKundNameById(kund.getId(), "pelle");
        verify(kr).updateNameById(kund.getId(), "pelle");
    }


    @Test
    void updateKundAddress(){
        KundService service1 = new KundService(kr);
        service1.updateKundAddressById(kund.getId(), "Solgatan 3");
        verify(kr).updateAddressById(kund.getId(), "Solgatan 3");
    }


    //optional för att inte orsaka NullPointerException
    @Test
    void findByKundId(){
        when(kr.findById(kund.getId())).thenReturn(Optional.ofNullable(kund));
        KundService service1 = new KundService(kr);
        Optional<Kund> foundKund = service1.findById(id);

        assertTrue(foundKund.isPresent());
        assertEquals(kund.getId(), foundKund.get().getId());
        assertEquals(kund.getNamn(), foundKund.get().getNamn());
        assertEquals(kund.getAdress(), foundKund.get().getAdress());
    }

    @Test
    void detailedKundDto(){
//Det här testet kontrollerar om kundToDetDetailedKundDto
// i KundService konverterar en kund till en DetailedKundDto
        DetailedKundDto actual = service.kundToDetDetailedKundDto(kund);

        assertEquals(actual.getId(), detailedKundDto.getId());
        assertEquals(actual.getNamn(), detailedKundDto.getNamn());
        assertEquals(actual.getAdress(), detailedKundDto.getAdress());

    }
}