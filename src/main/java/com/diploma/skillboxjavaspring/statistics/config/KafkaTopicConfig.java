package com.diploma.skillboxjavaspring.statistics.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Kafka topic configuration for statistical events.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Kafka topic for user registration events.
     */
    public static final String USER_REGISTERED_TOPIC = "user-registered";

    /**
     * Kafka topic for room booking events.
     */
    public static final String ROOM_BOOKED_TOPIC = "room-booked";

    /**
     * Creates the Kafka topic for user registration events.
     *
     * @return Kafka topic with one partition and one replica
     */
    @Bean
    public NewTopic userRegisteredTopic() {
        return new NewTopic(USER_REGISTERED_TOPIC, 1, (short) 1);
    }

    /**
     * Creates the Kafka topic for room booking events.
     *
     * @return Kafka topic with one partition and one replica
     */
    @Bean
    public NewTopic roomBookedTopic() {
        return new NewTopic(ROOM_BOOKED_TOPIC, 1, (short) 1);
    }
}