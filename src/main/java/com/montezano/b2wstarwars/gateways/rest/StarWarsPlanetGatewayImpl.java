package com.montezano.b2wstarwars.gateways.rest;

import com.montezano.b2wstarwars.gateways.StarWarsPlanetGateway;
import com.montezano.b2wstarwars.http.data.StarWarsPlanetPageDataContract;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class StarWarsPlanetGatewayImpl implements StarWarsPlanetGateway {

    @Override
    public Flux<StarWarsPlanetPageDataContract> getAllPlanets(final Integer page) {
        return WebClient.builder().baseUrl(StarWarsApiConstants.serviceName)
                .build()
                .get()
                .uri("/planets/?page=" + page)
                .retrieve()
                .bodyToFlux(StarWarsPlanetPageDataContract.class);
    }
}
