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
            // Generate a series of events for each room
            generateRoomEvents(room, random);
        }
    }

    private void generateRoomEvents(Rum room, Random random) {
        LocalDateTime startTime = LocalDateTime.now().minusDays(random.nextInt(30));
        String[] cleaners = {"Per Persson", "Anna Andersson", "Kalle Karlsson"};

        for (int i = 0; i < 3; i++) { // Generate 3 sets of door and cleaning events
            LocalDateTime eventTime = startTime.plusDays(i).plusHours(random.nextInt(12));

            // Door opened
            createAndSaveEvent(room.getId(), "Dörren öppnad", eventTime, null);

            // Door closed
            createAndSaveEvent(room.getId(), "Dörren stängd", eventTime.plusMinutes(2), null);

            // Another door opened
            createAndSaveEvent(room.getId(), "Dörren öppnad", eventTime.plusHours(2), null);

            // Cleaning started
            String cleaner = cleaners[random.nextInt(cleaners.length)];
            createAndSaveEvent(room.getId(), "Städning påbörjat", eventTime.plusHours(2).plusMinutes(2), "Städning påbörjat av " + cleaner);

            // Door closed
            createAndSaveEvent(room.getId(), "Dörren stängd", eventTime.plusHours(2).plusMinutes(10), null);

            // Cleaning finished
            createAndSaveEvent(room.getId(), "Städning avslutat", eventTime.plusHours(4), "Städning avslutat av " + cleaner);

            // Door opened and closed again
            createAndSaveEvent(room.getId(), "Dörren öppnad", eventTime.plusHours(4).plusMinutes(20), null);
            createAndSaveEvent(room.getId(), "Dörren stängd", eventTime.plusHours(4).plusMinutes(24), null);
        }
    }

    private void createAndSaveEvent(Long roomId, String eventName, LocalDateTime eventTime, String description) {
        RoomEvent event = new RoomEvent();
        event.setRoomId(roomId);
        event.setEventName(eventName);
        event.setEventTime(eventTime);
        event.setDescription(description);
        roomEventRepository.save(event);
    }
}