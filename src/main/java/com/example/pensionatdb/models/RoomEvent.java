package com.example.pensionatdb.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomEvent.class, name = "roomEvent"),
        @JsonSubTypes.Type(value = SpecialRoomEvent.class, name = "specialRoomEvent")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class RoomEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long roomId;

    @NotEmpty
    private String eventName;

    @NotNull
    private LocalDateTime eventTime;

    private String description;
}
