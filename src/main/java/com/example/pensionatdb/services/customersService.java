package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.customersDTO;
import com.example.pensionatdb.models.allcustomers;
import com.example.pensionatdb.models.customers;
import com.example.pensionatdb.repos.customersRepo;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.xml.catalog.Catalog;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class customersService {
    private final customersRepo customersRepo;
    private final XmlStreamProvider xmlStreamProvider;

    public customersDTO customersToCustomersDTO(customers c) {
        return customersDTO.builder()
                .id(c.getId())
                .companyName(c.getCompanyName())
                .contactName(c.getContactName())
                .contactTitle(c.getContactTitle())
                .streetAddress(c.getStreetAddress())
                .city(c.getCity())
                .postalCode(c.getPostalCode())
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

    public List<customers> getCustomers() throws IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        InputStream stream = xmlStreamProvider.getDataStream();
        allcustomers theCustomers = xmlMapper.readValue(stream, allcustomers.class);
        return theCustomers.getCustomers();
    }

    protected void mapCustomer(customers target, customers source) {
        target.setCompanyName(source.getCompanyName());
        target.setContactName(source.getContactName());
        target.setContactTitle(source.getContactTitle());
        target.setStreetAddress(source.getStreetAddress());
        target.setCity(source.getCity());
        target.setPostalCode(source.getPostalCode());
        target.setCountry(source.getCountry());
        target.setPhone(source.getPhone());
        target.setFax(source.getFax());
    }

    protected void saveCustomer(customers customer) {
        customersRepo.save(customer);
    }

    public void fetchAndSaveCustomers() throws IOException {
        for (customers c : getCustomers()) {
            customers customer;
            Optional<customers> fromDatabase = customersRepo.findById(c.getId());
            if (fromDatabase.isPresent()) {
                customer = fromDatabase.get();
            } else {
                customer = new customers();
            }

            mapCustomer(customer, c);
            saveCustomer(customer);
        }
    }

    public List<customers> getAllCustomers() {
        return customersRepo.findAll();
    }

    public List<customers> findAllByCompanyNameContainsOrContactNameContainsOrCountryContainsDTO(
            String companyName, String contactName, String country, Sort sort) {
        return customersRepo.findAllByCompanyNameContainsOrContactNameContainsOrCountryContains(
                companyName, contactName, country, sort);
    }

    public List<customers> findAllSortDTO(Sort sort) {
        return customersRepo.findAll(sort);
    }

    public customersDTO getCustomerById(Long customerId) {
        customers customer = customersRepo.findById(customerId).orElse(null);
        if (customer != null) {
            return convertToCustomersDTO(customer);
        } else {
            // Handle the case where the customer is not found
            return null;
        }
    }
}