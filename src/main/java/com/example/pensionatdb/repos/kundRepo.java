package com.example.pensionatdb.repos;

import com.example.pensionatdb.models.Kund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface kundRepo extends JpaRepository<Kund, Long> {
}
