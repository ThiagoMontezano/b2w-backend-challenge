package com.montezano.b2wstarwars.gateways.kafka.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.montezano.b2wstarwars.domains.Planet;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlanetPropagatorOfKafka {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @EventListener
    public void capture(final Planet planet) {
        try {
            kafkaTemplate.send(Topics.STAR_WARS_PLANET_EVENTS,
                    planet.getName(),
                    objectMapper.writeValueAsString(planet))
                    .get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
