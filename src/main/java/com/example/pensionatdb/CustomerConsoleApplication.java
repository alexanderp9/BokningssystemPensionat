package com.example.pensionatdb;


import com.example.pensionatdb.models.allcustomers;
import com.example.pensionatdb.models.customers;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.pensionatdb.repos.customersRepo;

import java.net.URL;
import java.util.List;


@SpringBootApplication
public class CustomerConsoleApplication implements CommandLineRunner {

    @Autowired
    private customersRepo customersRepository;

    @Override
    public void run(String... args) throws Exception {
        // Skapa en JacksonXmlModule och XmlMapper för att pars XML-data
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);

        // Hämta XML-data från länken
        URL url = new URL("https://javaintegration.systementor.se/customers");
        allcustomers allCustomers = xmlMapper.readValue(url, allcustomers.class);

        // Hämta befintliga kunder från databasen
        List<customers> existingCustomers = customersRepository.findAll();

        // Loopa genom kunderna från XML och spara dem i databasen
        for (customers customer : allCustomers.getCustomers()) {
            // Kontrollera om kunden redan finns i databasen
            boolean customerExists = existingCustomers.stream()
                    .anyMatch(existingCustomer -> existingCustomer.getCompanyName().equals(customer.getCompanyName()));

            // Om kunden inte finns i databasen, spara den
            if (!customerExists) {
                customersRepository.save(customer);
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(CustomerConsoleApplication.class, args);
    }
}