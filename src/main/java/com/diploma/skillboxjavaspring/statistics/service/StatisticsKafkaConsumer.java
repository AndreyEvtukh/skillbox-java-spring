package com.diploma.skillboxjavaspring.statistics.service;

import com.diploma.skillboxjavaspring.statistics.config.KafkaTopicConfig;
import com.diploma.skillboxjavaspring.statistics.dto.RoomBookedEvent;
import com.diploma.skillboxjavaspring.statistics.dto.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsKafkaConsumer {

    private final StatisticsService statisticsService;

    @KafkaListener(
            topics = KafkaTopicConfig.USER_REGISTERED_TOPIC,
            groupId = "statistics-service"
    )
    public void handleUserRegistered(UserRegisteredEvent event) {
        statisticsService.saveUserRegisteredEvent(event);
    }

    @KafkaListener(
            topics = KafkaTopicConfig.ROOM_BOOKED_TOPIC,
            groupId = "statistics-service"
    )
    public void handleRoomBooked(RoomBookedEvent event) {
        statisticsService.saveRoomBookedEvent(event);
    }
}