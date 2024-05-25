package com.example.pensionatdb.Tests;

import com.example.pensionatdb.models.customers;
import com.example.pensionatdb.repos.customersRepo;
import com.example.pensionatdb.services.XmlStreamProvider;
import com.example.pensionatdb.services.customersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class customersTest {

    private XmlStreamProvider xmlStreamProvider = mock(XmlStreamProvider.class);
    private customersRepo cr = mock(customersRepo.class);

    customersService sut;

    @BeforeEach()
    void setup(){
        sut = new customersService(cr,xmlStreamProvider);
    }

    @Test
    void whenGetCustomersShouldMapCorrectly() throws IOException {

        when(xmlStreamProvider.getDataStreamCustomers()).thenReturn(getClass().getClassLoader().getResourceAsStream("customers.xml"));


        List<customers> result = sut.getCustomers();


        assertEquals(3, result.size() );
        assertEquals("Kramland", result.get(0).getCity() );
        assertEquals("Sverige", result.get(0).getCountry() );
        assertEquals("gardener", result.get(0).getContactTitle() );

    }

}
