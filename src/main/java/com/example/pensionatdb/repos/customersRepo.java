package com.example.pensionatdb.repos;

import com.example.pensionatdb.models.customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface customersRepo extends JpaRepository<customers, Long> {

    List<customers> findAll();
}
