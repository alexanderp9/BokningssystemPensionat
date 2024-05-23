package com.example.pensionatdb;

import com.example.pensionatdb.dtos.RoomEventDTO;
import com.example.pensionatdb.models.RoomEvent;
import com.example.pensionatdb.models.SpecialRoomEvent;
import com.example.pensionatdb.services.RoomEventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class EventConsumer {

    private final String queueName = "dd007e46-8d2d-47f7-9429-3bf8cb5802c9";
    private final RoomEventService roomEventService;
    private final ObjectMapper mapper;

    @Autowired
    public EventConsumer(RoomEventService roomEventService) {
        this.roomEventService = roomEventService;
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        configureObjectMapper();
        System.out.println("EventConsumer initialized with RoomEventService");
    }

    private void configureObjectMapper() {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(RoomEvent.class)
                .build();

        SimpleModule module = new SimpleModule();
        module.registerSubtypes(SpecialRoomEvent.class);

        mapper.registerModule(module)
                .activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
    }

    public void consumeEvents() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("128.140.81.47");
        factory.setUsername("djk47589hjkew789489hjf894");
        factory.setPassword("sfdjkl54278frhj7");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

            try {
                // Konvertera JSON-meddelandet till ett RoomEvent-objekt
                RoomEvent roomEvent = mapper.readValue(message, RoomEvent.class);
                System.out.println(" [x] Parsed RoomEvent: " + roomEvent);

                RoomEventDTO roomEventDTO = roomEventService.convertToDTO(roomEvent);
                System.out.println(" [x] Converted to DTO: " + roomEventDTO);

                // Gör insättning i databasen
                roomEventService.saveRoomEvent(roomEventDTO);
                System.out.println(" [x] Inserted event into database");
            } catch (Exception e) {
                System.err.println(" [!] Error processing message: " + e.getMessage());
                e.printStackTrace();
            }
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
