package com.example.pensionatdb.services;

import com.example.pensionatdb.dtos.RoomEventDTO;
import com.example.pensionatdb.models.RoomEvent;
import com.example.pensionatdb.repos.RoomEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomEventService {

    private final RoomEventRepository roomEventRepository;

    @Autowired
    public RoomEventService(RoomEventRepository roomEventRepository) {
        this.roomEventRepository = roomEventRepository;
    }

    public List<RoomEventDTO> getAllRoomEvents() {
        List<RoomEvent> roomEvents = roomEventRepository.findAll();
        return roomEvents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoomEventDTO saveRoomEvent(RoomEventDTO roomEventDTO) {
        RoomEvent roomEvent = convertToEntity(roomEventDTO);
        RoomEvent savedRoomEvent = roomEventRepository.save(roomEvent);
        return convertToDTO(savedRoomEvent);
    }

    // Andra metoder för att hämta, uppdatera eller ta bort rumshändelser kan läggas till här

    public List<RoomEventDTO> getRoomEventsByRoomId(Long roomId) {
        List<RoomEvent> roomEvents = roomEventRepository.findByRoomId(roomId);
        return roomEvents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoomEventDTO convertToDTO(RoomEvent roomEvent) {
        RoomEventDTO dto = new RoomEventDTO();
        dto.setId(roomEvent.getId());
        dto.setRoomId(roomEvent.getRoomId());
        dto.setEventName(roomEvent.getEventName());
        dto.setEventTime(roomEvent.getEventTime());
        dto.setDescription(roomEvent.getDescription());
        return dto;
    }

    private RoomEvent convertToEntity(RoomEventDTO dto) {
        RoomEvent entity = new RoomEvent();
        entity.setId(dto.getId());
        entity.setRoomId(dto.getRoomId());
        entity.setEventName(dto.getEventName());
        entity.setEventTime(dto.getEventTime());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
