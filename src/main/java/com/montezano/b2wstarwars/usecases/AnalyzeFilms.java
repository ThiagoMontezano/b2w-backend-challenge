package com.montezano.b2wstarwars.usecases;

import com.montezano.b2wstarwars.domains.Planet;
import com.montezano.b2wstarwars.gateways.PlanetGateway;
import com.montezano.b2wstarwars.gateways.StarWarsPlanetGateway;
import com.montezano.b2wstarwars.gateways.rest.StarWarsApiConstants;
import com.montezano.b2wstarwars.http.data.StarWarsPlanetPageDataContract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AnalyzeFilms {

    private final PlanetGateway planetGateway;

    private final StarWarsPlanetGateway starWarsPlanetGateway;

    public void verify(final Planet planet) {
        long numberOfAppearancesInFilms = amount(planet.getName(), 1);
        planet.setNumberOfAppearancesInFilms(numberOfAppearancesInFilms);
        planetGateway.save(planet).block();
    }

    private long amount(final String planetName, Integer page) {
        Optional<StarWarsPlanetPageDataContract> dataContract = starWarsPlanetGateway
                .getAllPlanets(page)
                .collectList()
                .block()
                .stream()
                .findFirst();

        if (dataContract.isPresent()) {
            return dataContract.map(dc -> dc.results
                    .stream()
                    .filter(planet -> planet.getName().equalsIgnoreCase(planetName))
                    .findFirst()
                    .map(planet -> planet.filmsUrls.stream().count())
                    .orElse(dc.hasMore() ? amount(planetName, page + 1) : Long.valueOf(StarWarsApiConstants.zero)))
                    .get();
        }
        return Long.valueOf(StarWarsApiConstants.zero);
    }
}
