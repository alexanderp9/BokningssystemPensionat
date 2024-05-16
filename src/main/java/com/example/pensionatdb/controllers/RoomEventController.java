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
    private final EventSeeder eventSeeder;

    @Autowired
    public RoomEventController(RoomEventService roomEventService, EventSeeder eventSeeder) {
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

    @GetMapping("/seed")
    public void seedEvents() {
        eventSeeder.seedEvents();
    }

    @GetMapping("/{roomId}")
    public List<RoomEventDTO> getRoomEventsByRoomId(@PathVariable Long roomId) {
        return roomEventService.getRoomEventsByRoomId(roomId);
    }
}
