package com.example.pensionatdb.services;

import com.example.pensionatdb.models.Role;
import com.example.pensionatdb.models.User;
import com.example.pensionatdb.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void createUser(User user) {
        // Lösenordet måste krypteras innan det sparas i databasen
        user.setPassword(encryptPassword(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(Long id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(encryptPassword(updatedUser.getPassword()));
            user.setRole(updatedUser.getRole());
            userRepository.save(user);
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Metod för att kryptera lösenordet
    private String encryptPassword(String password) {
        // Implementera logik för att kryptera lösenordet, t.ex. med BCryptPasswordEncoder
        return password; // För närvarande returneras lösenordet i klartext (inte säkert)
    }
}
