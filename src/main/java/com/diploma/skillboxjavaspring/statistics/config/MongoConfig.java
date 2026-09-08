package com.diploma.skillboxjavaspring.statistics.config;

import org.bson.UuidRepresentation;
import org.springframework.boot.mongodb.autoconfigure.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MongoDB configuration for the statistics module.
 */
@Configuration
public class MongoConfig {

    /**
     * Configures the UUID representation used by the MongoDB driver.
     *
     * @return MongoDB client settings customizer using standard UUID representation
     */
    @Bean
    public MongoClientSettingsBuilderCustomizer uuidRepresentationCustomizer() {
        return builder ->
                builder.uuidRepresentation(UuidRepresentation.STANDARD);
    }
}