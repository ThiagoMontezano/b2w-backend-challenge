package com.montezano.b2wstarwars.gateways;

import com.montezano.b2wstarwars.domains.Planet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class PlanetGatewayInMemory implements PlanetGateway {

    private static Map<String, Planet> dataStore = new HashMap<>();

    @Override
    public Mono<Planet> save(final Planet planet) {
        dataStore.put(planet.getId(), planet);
        return Mono.just(planet);
    }

    @Override
    public Mono<Planet> findByName(final String name) {
        return Mono.just(dataStore
                .values()
                .stream()
                .filter(p->p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(new Planet()));
    }

    @Override
    public Mono<Planet> findById(final String id) {
        return Mono.just(dataStore.get(id));
    }

    @Override
    public Flux<Planet> findAll() {
        return Flux.fromIterable(dataStore.values());
    }

    @Override
    public Mono<Void> delete(final Planet planet) {
        dataStore.remove(planet.getId());
        return Mono.empty();
    }

    public void clear() {
        dataStore.clear();
    }
}