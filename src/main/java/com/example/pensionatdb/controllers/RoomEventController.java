package com.example.pensionatdb.controllers;

import com.example.pensionatdb.dtos.RoomEventDTO;
import com.example.pensionatdb.services.RoomEventService;
import com.example.pensionatdb.seeders.EventSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@RestController
@RequestMapping("/room-events")
public class RoomEventController {

    private final RoomEventService roomEventService;
    private final EventSeeder eventSeeder; // Lägg till en instans av EventSeeder

    @Autowired
    public RoomEventController(RoomEventService roomEventService, EventSeeder eventSeeder) { // Injicera EventSeeder i konstruktorn
        this.roomEventService = roomEventService;
        this.eventSeeder = eventSeeder;
    }

    @GetMapping
    public List<RoomEventDTO> getAllRoomEvents() {
        return roomEventService.getAllRoomEvents();
    }

    @PostMapping
    public RoomEventDTO createRoomEvent(@RequestBody RoomEventDTO roomEventDTO) {
        return roomEventService.saveRoomEvent(roomEventDTO);
    }

    @GetMapping("/seed") // Skapa en ny endpoint för att köra seed-metoden
    public void seedEvents() {
        eventSeeder.seedEvents(); // Anropa seed-metoden från EventSeeder
    }

    // Andra endpoint-metoder för att hantera andra operationer på rumshändelser kan läggas till här

    @GetMapping("/{roomId}")
    public String getRoomEventsByRoomId(@PathVariable Long roomId, Model model) {
        List<RoomEventDTO> roomEvents = roomEventService.getRoomEventsByRoomId(roomId);
        model.addAttribute("roomEvents", roomEvents);
        return "RoomEvent";
    }


}
