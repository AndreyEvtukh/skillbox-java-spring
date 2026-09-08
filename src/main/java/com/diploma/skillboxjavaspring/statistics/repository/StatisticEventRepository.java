package com.diploma.skillboxjavaspring.statistics.repository;

import com.diploma.skillboxjavaspring.statistics.entity.StatisticEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for storing and retrieving statistical events in MongoDB.
 */
public interface StatisticEventRepository
        extends MongoRepository<StatisticEvent, String> {
}