package com.example.pensionatdb.dtos;

import java.time.LocalDateTime;

public class RoomEventDTO {

    private Long id;
    private Long roomId;
    private String eventName;
    private LocalDateTime eventTime;
    private String description;

    // Constructors, getters, and setters
    public RoomEventDTO() {
    }

    public RoomEventDTO(Long id, Long roomId, String eventName, LocalDateTime eventTime, String description) {
        this.id = id;
        this.roomId = roomId;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RoomEventDTO{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", eventName='" + eventName + '\'' +
                ", eventTime=" + eventTime +
                ", description='" + description + '\'' +
                '}';
    }
}
