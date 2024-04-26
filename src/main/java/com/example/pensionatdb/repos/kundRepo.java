package com.example.pensionatdb.repos;

import com.example.pensionatdb.models.Kund;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface kundRepo extends JpaRepository<Kund, Long> {

    @Modifying
    @Transactional

    @Query("update Kund k set k.namn = :newNamn where k.id = :id")
    void updateNameById(Long id, String newNamn);

    @Modifying
    @Transactional
    @Query("update Kund k set k.adress = :newAddress where k.id = :id")
    void updateAddressById(Long id, String newAddress);

}
