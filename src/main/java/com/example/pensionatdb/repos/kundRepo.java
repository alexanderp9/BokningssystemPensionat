package com.example.pensionatdb.repos;

import com.example.pensionatdb.models.Kund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface kundRepo extends JpaRepository<Kund, Long> {

    @Query("SELECT COUNT(b) FROM Bokning b WHERE b.kund = ?1")  // Hämta kundens bokningar
    int countBokningarForKund(Kund kund);

    @Transactional(readOnly = true)
    default boolean canDeleteKund(Kund kund) {
        return countBokningarForKund(kund) == 0;
    }

    @Modifying
    @Transactional

    @Query("update Kund k set k.namn = :newNamn where k.id = :id")
    void updateNameById(Long id, String newNamn);

    @Modifying
    @Transactional
    @Query("update Kund k set k.adress = :newAddress where k.id = :id")
    void updateAddressById(Long id, String newAddress);



}
