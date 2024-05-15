package com.example.pensionatdb.seeders;

import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.models.RoomEvent;
import com.example.pensionatdb.repos.RoomEventRepository;
import com.example.pensionatdb.repos.rumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class EventSeeder {

    private final rumRepo roomRepository;
    private final RoomEventRepository roomEventRepository;

    @Autowired
    public EventSeeder(rumRepo roomRepository, RoomEventRepository roomEventRepository) {
        this.roomRepository = roomRepository;
        this.roomEventRepository = roomEventRepository;
    }

    public void seedEvents() {
        List<Rum> rooms = roomRepository.findAll();
        Random random = new Random();

        for (Rum room : rooms) {
            for (int i = 0; i < 10; i++) { // Generate 10 random events for each room
                RoomEvent event = new RoomEvent();
                event.setRoomId(room.getId());
                event.setEventName(generateRandomEvent());
                event.setEventTime(LocalDateTime.now().minusDays(random.nextInt(30))); // Random date within the last 30 days
                event.setDescription("Random event description");
                roomEventRepository.save(event);
            }
        }
    }

    private String generateRandomEvent() {
        // Implement logic to generate random event names
        return "Random Event";
    }
}
