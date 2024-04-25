package com.example.pensionatdb.repos;

import com.example.pensionatdb.models.Kund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface kundRepo extends JpaRepository<Kund, Long> {

    @Query("SELECT COUNT(b) FROM Bokning b WHERE b.kund = ?1")  // Hämta kundens bokningar
    int countBokningarForKund(Kund kund);

    @Transactional(readOnly = true)
    default boolean canDeleteKund(Kund kund) {
        return countBokningarForKund(kund) == 0;
    }
}
