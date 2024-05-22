package com.example.pensionatdb.repos;

import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.models.Rum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface bokningRepo extends JpaRepository<Bokning, Long> {

    @Transactional
    List<Bokning> findByKund(Kund kund);

    List<Bokning> findByRum(Rum rum);

    List<Bokning> findByRumAndStartDatumLessThanEqualAndSlutDatumGreaterThanEqual(Rum rum, LocalDate endDate, LocalDate startDate);

    List<Bokning> findByKundAndStartDatumAfter(Kund kund, LocalDate startDatum);
}
