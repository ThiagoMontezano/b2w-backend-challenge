package com.montezano.b2wstarwars.gateways.impl;

import com.montezano.b2wstarwars.domains.Planet;
import com.montezano.b2wstarwars.gateways.PublishEventGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PublishEventGatewayImpl implements PublishEventGateway {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publishPlanet(final Planet planet) {
        publisher.publishEvent(planet);
    }
}
