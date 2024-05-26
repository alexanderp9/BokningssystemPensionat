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
    public double discountBokning(Kund kund, int nätter, LocalDate startDate, LocalDate endDate, double roomPrice) {
        double discount = 0.0;

        // rabatt för bokning 2 elr fler nätter
        if (nätter >= 2) {
            discount += 0.005;
        }
        // natt från söndag till måndag rabatt
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY && date.plusDays(1).getDayOfWeek() == DayOfWeek.MONDAY) {
                discount += 0.02;
                break;
            }
        }
        // rabatt för kund med mer än 10 nätter bokade under 1 år
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        List<Bokning> bokningar = bokningRepo.findByKundAndStartDatumAfter(kund, oneYearAgo);
        int totalNights = bokningar.stream().mapToInt(Bokning::getNätter).sum();

        if (totalNights >= 10) {
            discount += 0.02;
        }

        return roomPrice * nätter * (1 - discount);
    }
}
