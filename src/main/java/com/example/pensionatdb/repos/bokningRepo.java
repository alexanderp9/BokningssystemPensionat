package com.example.pensionatdb.repos;

import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface bokningRepo extends JpaRepository<Bokning, Long> {

    @Modifying
    @Transactional
    @Query("SELECT b FROM Bokning b WHERE b.kund = :kund")
    List<Bokning> findByKund(Kund kund);

}
