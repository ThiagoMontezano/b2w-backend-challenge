package com.montezano.b2wstarwars.gateways.impl;

import com.montezano.b2wstarwars.domains.Planet;
import com.montezano.b2wstarwars.gateways.PlanetGateway;
import com.montezano.b2wstarwars.gateways.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PlanetGatewayImpl implements PlanetGateway {

    private final PlanetRepository planetRepository;

    @Override
    public Mono<Planet> save(final Planet planet) {
        return planetRepository.save(planet);
    }

    @Override
    public Mono<Planet> findByName(final String name) {
        return planetRepository.findByName(name);
    }

    @Override
    public Mono<Planet> findById(final String id) {
        return planetRepository.findById(id);
    }

    @Override
    public Flux<Planet> findAll() {
        return planetRepository.findAll();
    }

    @Override
    public Mono<Void> delete(final Planet planet) {
        return planetRepository.delete(planet);
    }

}
