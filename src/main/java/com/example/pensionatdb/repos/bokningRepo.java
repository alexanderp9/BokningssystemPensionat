package com.example.pensionatdb.repos;

import com.example.pensionatdb.models.Bokning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface bokningRepo extends JpaRepository<Bokning, Long> {
}
