package com.diploma.skillboxjavaspring.statistics.service;

import com.diploma.skillboxjavaspring.statistics.dto.RoomBookedEvent;
import com.diploma.skillboxjavaspring.statistics.dto.UserRegisteredEvent;
import com.diploma.skillboxjavaspring.statistics.entity.StatisticEvent;
import com.diploma.skillboxjavaspring.statistics.repository.StatisticEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticEventRepository repository;

    public void saveUserRegisteredEvent(UserRegisteredEvent event) {

        StatisticEvent statisticEvent = StatisticEvent.builder()
                .eventId(event.eventId())
                .eventType(event.eventType())
                .occurredAt(event.occurredAt())
                .userId(event.userId())
                .build();

        repository.save(statisticEvent);
    }

    public void saveRoomBookedEvent(RoomBookedEvent event) {

        StatisticEvent statisticEvent = StatisticEvent.builder()
                .eventId(event.eventId())
                .eventType(event.eventType())
                .occurredAt(event.occurredAt())
                .userId(event.userId())
                .checkIn(event.checkIn())
                .checkOut(event.checkOut())
                .build();

        repository.save(statisticEvent);
    }

    public List<StatisticEvent> findAll() {
        List<StatisticEvent> events = repository.findAll();

        log.info("=> Statistics from MongoDB: {}", events);

        return events;
    }
}