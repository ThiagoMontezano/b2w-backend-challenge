package com.montezano.b2wstarwars.gateways;

import com.montezano.b2wstarwars.domains.Planet;

public interface PublishEventGateway {

    void publishPlanet(final Planet planet);
}
