package com.example.pensionatdb.repos;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Rum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface bokningRepo extends JpaRepository<Bokning, Long> {
    List<Bokning> findByRumAndStartSlutDatumBetween(Rum rum, String startDate, String endDate);
}
