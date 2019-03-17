package com.montezano.b2wstarwars.gateways;

import com.montezano.b2wstarwars.domains.Planet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlanetGateway {

    Mono<Planet> save(final Planet planet);

    Mono<Planet> findByName(final String name);

    Mono<Planet> findById(final String id);

    Flux<Planet> findAll();

    Mono<Void> delete(final Planet planet);
}
