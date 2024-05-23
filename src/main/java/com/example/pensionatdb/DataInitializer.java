package com.example.pensionatdb;

import com.example.pensionatdb.models.Role;
import com.example.pensionatdb.models.User;
import com.example.pensionatdb.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Skapa en standardadminanvändare
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123")); // Lösenordet bör vara krypterat
        admin.setRole(Role.ADMIN);

        // Skapa en standardreceptionistanvändare
        User receptionist = new User();
        receptionist.setUsername("receptionist");
        receptionist.setPassword(passwordEncoder.encode("receptionist123")); // Lösenordet bör vara krypterat
        receptionist.setRole(Role.RECEPTIONIST);

        // Spara användarna i databasen
        userRepository.save(admin);
        userRepository.save(receptionist);
    }
}

