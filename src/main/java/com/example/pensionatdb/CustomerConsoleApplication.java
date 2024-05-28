package com.example.pensionatdb;


import com.example.pensionatdb.models.allcustomers;
import com.example.pensionatdb.models.customers;
import com.example.pensionatdb.services.XmlStreamProvider;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.pensionatdb.repos.customersRepo;
import org.springframework.context.annotation.ComponentScan;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


@ComponentScan
public class CustomerConsoleApplication implements CommandLineRunner {

    @Autowired
    private customersRepo customersRepository;

    @Autowired
    private XmlStreamProvider xmlStreamProvider;

    @Override
    public void run(String... args) throws Exception {
        XmlMapper xmlMapper = new XmlMapper(new JacksonXmlModule());
        InputStream inputStream = xmlStreamProvider.getDataStreamCustomers();
        customers[] allCustomers = xmlMapper.readValue(inputStream, customers[].class);

        List<customers> existingCustomers = customersRepository.findAll();

        Arrays.stream(allCustomers)
                .filter(customer -> existingCustomers.stream().noneMatch(existingCustomer -> existingCustomer
                        .getCompanyName().equals(customer.getCompanyName())))
                .forEach(customersRepository::save);
    }
    public static void main(String[] args) {
        SpringApplication.run(CustomerConsoleApplication.class, args);
    }
}