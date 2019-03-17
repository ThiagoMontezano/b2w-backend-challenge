package com.montezano.b2wstarwars.gateways;

import com.montezano.b2wstarwars.http.data.StarWarsPlanetPageDataContract;
import reactor.core.publisher.Flux;

public interface StarWarsPlanetGateway {

    Flux<StarWarsPlanetPageDataContract> getAllPlanets(final Integer page);
}
