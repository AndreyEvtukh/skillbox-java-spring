package com.diploma.skillboxjavaspring.statistics.service;

import com.diploma.skillboxjavaspring.statistics.config.KafkaTopicConfig;
import com.diploma.skillboxjavaspring.statistics.dto.RoomBookedEvent;
import com.diploma.skillboxjavaspring.statistics.dto.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishUserRegistered(UserRegisteredEvent event) {
        kafkaTemplate.send(
                KafkaTopicConfig.USER_REGISTERED_TOPIC,
                event.eventId().toString(),
                event
        );
    }

    public void publishRoomBooked(RoomBookedEvent event) {
        kafkaTemplate.send(
                KafkaTopicConfig.ROOM_BOOKED_TOPIC,
                event.eventId().toString(),
                event
        );
    }
}