package com.example.pensionatdb;

import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.bokningRepo;
import com.example.pensionatdb.repos.kundRepo;
import com.example.pensionatdb.repos.rumRepo;
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
    public CommandLineRunner demo(kundRepo kundrepo, rumRepo rumrepo, bokningRepo bokningrepo){
        return (args) -> {


            Kund k1 = new Kund(1L,"pelle", "Storgatan 23");
            Kund k2 = new Kund(2L,"Fralle", "Solgatan 55");
            Kund k3 = new Kund(3L,"Nalle", "Väggatan 33");
            Kund k4 = new Kund(4L,"fdsfsd", "asdsadsad");

            kundrepo.save(k1);
            kundrepo.save(k2);
            kundrepo.save(k3);
            kundrepo.save(k4);


            Rum r1 = new Rum("Enkel", 0);
            Rum r2 = new Rum("Enkel", 0);
            Rum r3 = new Rum("Enkel", 0);

            rumrepo.save(r1);
            rumrepo.save(r2);
            rumrepo.save(r3);

            bokningrepo.save(new Bokning(2, "23",k1,r1));
            bokningrepo.save(new Bokning(1,"55",k2,r2 ));
            bokningrepo.save(new Bokning(3,"77",k3,r3));



        };

    }

}
