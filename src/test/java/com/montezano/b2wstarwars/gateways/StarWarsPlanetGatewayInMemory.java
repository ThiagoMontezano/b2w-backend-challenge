package com.montezano.b2wstarwars.gateways;

import com.montezano.b2wstarwars.http.data.StarWarsPlanetPageDataContract;
import lombok.Getter;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class StarWarsPlanetGatewayInMemory implements StarWarsPlanetGateway {

    private static Map<String, StarWarsPlanetPageDataContract> dataStore = new HashMap<>();
    @Getter
    private AtomicInteger id = new AtomicInteger(0);

    @Override
    public Flux<StarWarsPlanetPageDataContract> getAllPlanets(Integer page) {
        return Flux.fromIterable(dataStore.values());
    }

    public void create(final StarWarsPlanetPageDataContract dataContract) {
        dataStore.put(id.toString(), dataContract);
        this.id.incrementAndGet();
    }

    public void clear() {
        dataStore.clear();
    }
}
