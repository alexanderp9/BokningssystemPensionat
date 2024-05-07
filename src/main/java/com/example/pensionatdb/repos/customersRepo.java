package com.example.pensionatdb.repos;

import com.example.pensionatdb.models.Rum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface customersRepo extends JpaRepository<Rum, Long> {
}
