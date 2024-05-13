package com.example.pensionatdb.repos;

import com.example.pensionatdb.models.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface shippersRepo extends JpaRepository<Shipper, Long> {

    List<Shipper> findAll();
}
