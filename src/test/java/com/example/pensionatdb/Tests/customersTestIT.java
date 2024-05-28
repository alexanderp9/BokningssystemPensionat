package com.example.pensionatdb.Tests;

import com.example.pensionatdb.repos.customersRepo;
import com.example.pensionatdb.services.XmlStreamProvider;
import com.example.pensionatdb.services.customersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class customersTestIT {
    @Autowired
    customersRepo cr;
    @Autowired
    XmlStreamProvider xmlStreamProvider;

    customersService sut;

    @Test
    void getCustomersWillFetch() throws IOException {
        sut = new customersService(cr, xmlStreamProvider);
        Scanner s = new Scanner(sut.xmlStreamProvider.getDataStreamCustomers()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("<allcustomers>"));
        assertTrue(result.contains("</allcustomers>"));
        assertTrue(result.contains("<customers>"));
        assertTrue(result.contains("</customers>"));
        assertTrue(result.contains("<id>"));
        assertTrue(result.contains("</id>"));
        assertTrue(result.contains("<companyName>"));
        assertTrue(result.contains("</companyName>"));
        assertTrue(result.contains("<contactName>"));
        assertTrue(result.contains("</contactName>"));
        assertTrue(result.contains("<contactTitle>"));
        assertTrue(result.contains("</contactTitle>"));
        assertTrue(result.contains("<streetAddress>"));
        assertTrue(result.contains("</streetAddress>"));
        assertTrue(result.contains("<city>"));
        assertTrue(result.contains("</city>"));
        assertTrue(result.contains("<postalCode>"));
        assertTrue(result.contains("</postalCode>"));
        assertTrue(result.contains("<country>"));
        assertTrue(result.contains("</country>"));
        assertTrue(result.contains("<phone>"));
        assertTrue(result.contains("</phone>"));
        assertTrue(result.contains("<fax>"));
        assertTrue(result.contains("</fax>"));

    }

    @Test
    void getShipperWillFetch() throws IOException {
        sut = new customersService(cr, xmlStreamProvider);
        Scanner s = new Scanner(sut.xmlStreamProvider.getDataStreamCustomers()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("id"));
        assertTrue(result.contains("companyName"));
        assertTrue(result.contains("phone"));


    }

}