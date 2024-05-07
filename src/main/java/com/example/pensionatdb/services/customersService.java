package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.customersDTO;
import com.example.pensionatdb.models.customers;
import com.example.pensionatdb.repos.customersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class customersService {

    private final customersRepo cr;

    public customersDTO customersTocustomersDTO(customers c){
        return customersDTO.builder()
                .id(c.getId())
                .companyName(c.getCompanyName())
                .contactName(c.getContactName())
                .contactTitle(c.getContactTitle())
                .streetAddress(c.getStreetAddress())
                .city(c.getCity()).postalCode(c.getPostalCode())
                .country(c.getCountry())
                .phone(c.getPhone())
                .fax(c.getFax())
                .build();
    }
}
