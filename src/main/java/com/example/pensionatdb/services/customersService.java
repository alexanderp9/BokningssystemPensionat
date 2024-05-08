package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.customersDTO;
import com.example.pensionatdb.models.customers;
import com.example.pensionatdb.repos.customersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class customersService {
    private final customersRepo cr;

    public customersDTO customersToCustomersDTO(customers c){
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

    public customersDTO convertToCustomersDTO(customers entity) {
        customersDTO dto = new customersDTO();
        dto.setId(entity.getId());
        dto.setCompanyName(entity.getCompanyName());
        dto.setContactName(entity.getContactName());
        dto.setContactTitle(entity.getContactTitle());
        dto.setStreetAddress(entity.getStreetAddress());
        dto.setCity(entity.getCity());
        dto.setPostalCode(entity.getPostalCode());
        dto.setCountry(entity.getCountry());
        dto.setPhone(entity.getPhone());
        dto.setFax(entity.getFax());
        return dto;
    }

    public customers convertToEntity(customersDTO dto) {
        customers entity = new customers();
        entity.setId(dto.getId());
        entity.setCompanyName(dto.getCompanyName());
        entity.setContactName(dto.getContactName());
        entity.setContactTitle(dto.getContactTitle());
        entity.setStreetAddress(dto.getStreetAddress());
        entity.setCity(dto.getCity());
        entity.setPostalCode(dto.getPostalCode());
        entity.setCountry(dto.getCountry());
        entity.setPhone(dto.getPhone());
        entity.setFax(dto.getFax());
        return entity;
    }

    public List<customers> getAllCustomers() {
        return cr.findAll();
    }
}