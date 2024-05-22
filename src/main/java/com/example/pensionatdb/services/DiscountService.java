package com.example.pensionatdb.services;

import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.repos.bokningRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class DiscountService {

    private final bokningRepo bokningRepo;

    @Autowired
    public DiscountService(bokningRepo bokningRepo) {
        this.bokningRepo = bokningRepo;
    }

    public double calculateDiscount(Kund kund, LocalDate startDate, LocalDate endDate, double roomPrice) {
        double discount = 0.0;
        int nights = (int) (endDate.toEpochDay() - startDate.toEpochDay());

        if (nights >= 2) {
            discount += 0.005; // 0.5% rabatt för bokningar med två eller fler nätter
        }

        // om en av nätterna är söndag till måndag
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY && date.plusDays(1).getDayOfWeek() == DayOfWeek.MONDAY) {
                discount += 0.02; // 2% rabatt för natt från söndag till måndag
                break;
            }
        }

        // om kunden har bokat minst 10 nätter det senaste året
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        List<Bokning> bokningar = bokningRepo.findByKundAndStartDatumAfter(kund, oneYearAgo);
        int totalNights = bokningar.stream().mapToInt(Bokning::getNätter).sum();

        if (totalNights >= 10) {
            discount += 0.02; // Ytterligare 2% rabatt för kunder som bokat minst 10 nätter senaste året
        }

        return roomPrice * (1 - discount);
    }
}
