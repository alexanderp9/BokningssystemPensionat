package com.example.pensionatdb.Tests;

import com.example.pensionatdb.models.customers;
import com.example.pensionatdb.repos.customersRepo;
import com.example.pensionatdb.services.XmlStreamProvider;
import com.example.pensionatdb.services.customersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class customersTest {

    private XmlStreamProvider xmlStreamProvider = mock(XmlStreamProvider.class);
    private customersRepo customersRepo = mock(customersRepo.class);

    customersService sut;

    @BeforeEach()
    void setup(){
        sut = new customersService(customersRepo,xmlStreamProvider);
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
    @Test
    void fetchAndSaveCustomersShouldInsertNewRecords() throws IOException {
        // Arrange
        when(xmlStreamProvider.getDataStreamCustomers())
                .thenReturn(getClass().getClassLoader().getResourceAsStream("customers.xml"));
        when(customersRepo.findById(any(Long.class))).thenReturn(Optional.empty());

        // Act
        sut.fetchAndSaveCustomers();

        // Assert
        verify(customersRepo, times(3)).save(argThat(customer -> customer.getId() == null));
    }

    @Test
    void fetchAndSaveBooksShouldUpdateExistingRecords() throws IOException {
        // Arrange
        customers existing = new customers();
        existing.setId(123L);

        when(xmlStreamProvider.getDataStreamCustomers()).thenReturn(getClass().getClassLoader().getResourceAsStream("customers.xml"));
        when(customersRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        when(customersRepo.findById(123L)).thenReturn(Optional.of(existing));

        // Act
        sut.fetchAndSaveCustomers();

        // Assert
        verify(customersRepo, times(3)).save(argThat(customer -> customer.getId() == null || customer.getId().equals(123L)));
    }


}
