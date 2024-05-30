package com.example.pensionatdb;

import com.example.pensionatdb.models.Shipper;
import com.example.pensionatdb.repos.shippersRepo;
import com.example.pensionatdb.services.XmlStreamProvider;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

@ComponentScan
public class ShipperConsoleApplication implements CommandLineRunner {

    @Autowired
    private shippersRepo shippersRepo;

    @Autowired
    private XmlStreamProvider xmlStreamProvider;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // deserialiserar för att omvandla datat till java objekt
        // mapper konfigurerad till att ignorera okända egenskaper som vi inte bryr oss om.
        InputStream inputStream = xmlStreamProvider.getDataStreamShippers();
        Shipper[] shippers = mapper.readValue(inputStream, Shipper[].class);

        List<Shipper> existingShippers = shippersRepo.findAll();

        for (Shipper shipper : shippers) {
            boolean shipperExists = existingShippers.stream()
                    .anyMatch(existingShipper -> existingShipper.getId().equals(shipper.getId()));

            if (!shipperExists) {
                shippersRepo.save(shipper);
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ShipperConsoleApplication.class, args);
    }
}