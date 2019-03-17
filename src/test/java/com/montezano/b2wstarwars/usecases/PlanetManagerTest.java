package com.montezano.b2wstarwars.usecases;

import com.montezano.b2wstarwars.domains.Planet;
import com.montezano.b2wstarwars.gateways.*;
import com.montezano.b2wstarwars.http.data.StarWarsPlanetPageDataContract;
import com.montezano.b2wstarwars.mock.PlanetMockBuilder;
import com.montezano.b2wstarwars.mock.StarWarsPlanetPageDataContractMockBuilder;
import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PlanetManagerTest {

    private PlanetGateway planetGateway;

    private StarWarsPlanetGatewayInMemory starWarsPlanetGateway;

    private PublishEventGateway publishEventGateway;

    private PlanetManager planetManager;

    @Before
    public void setup() {
        planetGateway = new PlanetGatewayInMemory();
        starWarsPlanetGateway = new StarWarsPlanetGatewayInMemory();
        publishEventGateway = mock(PublishEventGateway.class);
        ((PlanetGatewayInMemory) planetGateway).clear();
        starWarsPlanetGateway.clear();
        planetManager = new PlanetManager(planetGateway, starWarsPlanetGateway, publishEventGateway);
    }

    @Test
    public void save() {
        final Planet planet = PlanetMockBuilder.build("terra");

        StepVerifier.create(planetManager.save(planet))
                .assertNext(it -> it.getId().equalsIgnoreCase(planet.getId()))
                .expectComplete()
                .verify();

        StepVerifier.create(planetManager.findById(planet.getId()))
                .assertNext(it -> it.getId().equalsIgnoreCase(planet.getId()))
                .expectComplete()
                .verify();

        StepVerifier.create(planetManager.findByName(planet.getName()))
                .assertNext(it -> it.getName().equalsIgnoreCase(planet.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void delete(){
        final Planet planet = PlanetMockBuilder.build("terra");

        StepVerifier.create(planetManager.save(planet))
                .assertNext(it -> it.getId().equalsIgnoreCase(planet.getId()))
                .expectComplete()
                .verify();

        StepVerifier.create(planetManager.deleteById(planet.getId()))
                .assertNext(it -> it.equals(Boolean.TRUE))
                .expectComplete()
                .verify();

    }

    @Test
    public void getAll() {
        final Planet planet = PlanetMockBuilder.build("terra");

        StepVerifier.create(planetManager.save(planet))
                .assertNext(it -> it.getId().equalsIgnoreCase(planet.getId()))
                .expectComplete()
                .verify();

        StepVerifier.create(planetManager.getAllPlanets())
                .assertNext(pl -> {
                    assertEquals(planet.getName(), pl.getName());
                    assertEquals(planet.getId(), planet.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void getAllStarWars() {
        StarWarsPlanetPageDataContract dataContract = new StarWarsPlanetPageDataContractMockBuilder().build();
        starWarsPlanetGateway.create(dataContract);
        StepVerifier.create(planetManager.getAllStarWarsPlanets(1))
                .assertNext(it -> assertTrue(it.hasMore()) )
                .expectComplete()
                .verify();
    }
}