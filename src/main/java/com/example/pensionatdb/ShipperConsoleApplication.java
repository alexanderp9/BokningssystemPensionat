package com.example.pensionatdb;

import com.example.pensionatdb.models.Shipper;
import com.example.pensionatdb.repos.shippersRepo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;
import java.util.List;

@SpringBootApplication
public class ShipperConsoleApplication implements CommandLineRunner {

    @Autowired
    private shippersRepo shippersRepo;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        //mapper för att ignorera de fält vi inte är intresserad av
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        URL url = new URL("https://javaintegration.systementor.se/shippers");
        Shipper[] shippers = mapper.readValue(url, Shipper[].class);

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
