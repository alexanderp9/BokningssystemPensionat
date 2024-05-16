package com.example.pensionatdb.repos;

import org.springframework.data.domain.Page;
import com.example.pensionatdb.models.customers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface customersRepo extends JpaRepository<customers, Long> {

    List<customers> findAll();

    List<customers> findAll(Sort sort);


    List<customers> findAllByCompanyNameContainsOrContactNameContainsOrCountryContains(String companyName,
                                                                                       String contactName,
                                                                                       String country, Sort sort);
}
