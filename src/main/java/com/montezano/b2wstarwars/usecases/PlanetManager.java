package com.montezano.b2wstarwars.usecases;

import com.montezano.b2wstarwars.domains.Planet;
import com.montezano.b2wstarwars.http.data.StarWarsPlanetPageDataContract;
import com.montezano.b2wstarwars.gateways.PlanetGateway;
import com.montezano.b2wstarwars.gateways.PublishEventGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class PlanetManager {

    private final PlanetGateway planetGateway;

    private final PublishEventGateway publishEventGateway;

    public Mono<Boolean> deleteById(final String id) {
        return planetGateway.findById(id)
                .flatMap(existingPlanet ->
                        planetGateway.delete(existingPlanet)
                                .then(Mono.just(Boolean.TRUE))
                )
                .defaultIfEmpty(Boolean.FALSE);
    }

    public Mono<Boolean> deleteByName(final String name) {
        return planetGateway.findByName(name)
                .flatMap(existingPlanet ->
                        planetGateway.delete(existingPlanet)
                                .then(Mono.just(Boolean.TRUE))
                )
                .defaultIfEmpty(Boolean.FALSE);
    }

    public Mono<Planet> save(final Planet planet) {
        return planetGateway.save(planet)
                .doOnSuccess(m -> publishEventGateway.publishPlanet(m));
    }

    public Flux<Planet> getAllPlanets(){
        return planetGateway.findAll().delayElements(Duration.ofMillis(300));
    }

    public Mono<Planet> findById(final String id) {
        return planetGateway.findById(id);
    }

    public Mono<Planet> findByName(final String name) {
        return planetGateway.findByName(name);
    }

    public Flux<StarWarsPlanetPageDataContract> getAllStarWarsPlanets(final Integer page){
        return planetGateway.getAllPlanets(page);
    }
}
