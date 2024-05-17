package com.example.pensionatdb.services;

import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.repos.kundRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class BlacklistService {

    private final kundRepo kundRepo;
    private static final Logger log = Logger.getLogger(BlacklistService.class.getName());

    public BlacklistService(kundRepo kundRepo) {
        this.kundRepo = kundRepo;
    }

    private final HttpClient client = HttpClient.newHttpClient();

    public ResponseEntity<String> addBlacklistMail(String email, boolean ok) {
        String url = "https://javabl.systementor.se/api/stefan/blacklist";
        String requestBody = "{\"email\":\"" + email + "\",\"name\":\"Kalle\",\"isOk\":\"" + ok + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new ResponseEntity<>(response.body(), HttpStatus.valueOf(response.statusCode()));
        } catch (Exception e) {
            log.severe("Error när /add användes " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<String> updateBlacklistMail(String email, boolean ok) {
        String url = "https://javabl.systementor.se/api/stefan/blacklist/" + email;
        String requestBody = "{\"name\":\"Kalle\",\"isOk\":\"" + ok + "\"}";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            return new ResponseEntity<>(response.body(), HttpStatus.valueOf(response.statusCode()));
        } catch (Exception e) {
            log.severe("Error vid /update: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public boolean checkBlacklist(String email) {
        String url = "https://javabl.systementor.se/api/stefan/blacklistcheck/" + email;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            boolean ok = rootNode.path("ok").asBoolean();

            return !ok;
        } catch (Exception e) {
            log.severe("Exception with att kolla mot listan " + e.getMessage());
            return false;
        }
    }

    public String getEmail(Long kundId) {
        Kund kund = kundRepo.findById(kundId).orElse(null);
        assert kund != null;
        return kund.getEmail();
    }

    public List<JsonNode> getAllBlacklisted() {
        String url = "https://javabl.systementor.se/api/stefan/blacklist";
        List<JsonNode> blacklisted = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseArray = objectMapper.readTree(response.body());

            for (JsonNode node : responseArray) {
                boolean ok = node.get("ok").asBoolean();
                if (!ok) {
                    blacklisted.add(node);
                }
            }
        } catch (Exception e) {
            log.severe("Error när listan skulle hämtas " + e.getMessage());
        }
        return blacklisted;
    }
}
