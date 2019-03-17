package com.montezano.b2wstarwars.gateways.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.montezano.b2wstarwars.domains.Planet;
import com.montezano.b2wstarwars.gateways.kafka.event.Topics;
import com.montezano.b2wstarwars.usecases.AnalyzeFilms;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanetListener {

    private final ObjectMapper objectMapper;

    private final AnalyzeFilms analyzeFilms;

    @KafkaListener(topics = Topics.STAR_WARS_PLANET_EVENTS)
    public void execute(final String message) {
        log.debug("Listening star wars planet on Kafka: {}", message);
        final Planet planet = toObject(message, Planet.class);
        analyzeFilms.verify(planet);
    }

    private  <T> T toObject(final String message, Class<T> clazz) {
        try {
            return objectMapper.readValue(message, clazz);
        } catch (IOException e) {
            log.error("Error parsing message: {}", message, e);
            return null;
        }
    }
}
