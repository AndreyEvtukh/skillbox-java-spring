package com.diploma.skillboxjavaspring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hotels API")
                        .description("""
                                REST API for a hotel booking service.
                                        The application provides hotel search by various criteria and ratings,
                                        hotel booking for a selected period, user rating from 1 to 5,
                                        and content management through an administrative CMS panel.
                                        Administrators can also export service statistics in CSV format.""")
                        .version("1.0.1")
                        .contact(new Contact()
                                .name("Andrey Evtukh")
                                .email("andrey.evtukh@gmail.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8082/")
                                .description("Development server")
                ));
    }
}
