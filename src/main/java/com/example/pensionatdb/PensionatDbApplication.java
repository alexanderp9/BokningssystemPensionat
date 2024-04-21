package com.example.pensionatdb;

import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.repos.kundRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PensionatDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(PensionatDbApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(kundRepo kundrepo){
        return (args) -> {
            kundrepo.save(new Kund("pelle", "Storgatan 23"));
            kundrepo.save(new Kund("Fralle", "Solgatan 55"));
            kundrepo.save(new Kund("Nalle", "Väggatan 33"));

        };

    }
}
