package com.montezano.b2wstarwars.usecases;

import com.montezano.b2wstarwars.domains.Planet;
import com.montezano.b2wstarwars.gateways.PlanetGateway;
import com.montezano.b2wstarwars.gateways.PlanetGatewayInMemory;
import com.montezano.b2wstarwars.gateways.StarWarsPlanetGatewayInMemory;
import com.montezano.b2wstarwars.http.data.StarWarsPlanetPageDataContract;
import com.montezano.b2wstarwars.mock.PlanetMockBuilder;
import com.montezano.b2wstarwars.mock.StarWarsPlanetPageDataContractMockBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AnalyzeFilmsTest {

    private PlanetGateway planetGateway;

    private StarWarsPlanetGatewayInMemory starWarsPlanetGateway;

    private AnalyzeFilms analyzeFilms;

    @Before
    public void setup() {
        planetGateway = new PlanetGatewayInMemory();
        starWarsPlanetGateway = new StarWarsPlanetGatewayInMemory();
        ((PlanetGatewayInMemory) planetGateway).clear();
        starWarsPlanetGateway.clear();
        analyzeFilms = new AnalyzeFilms(planetGateway, starWarsPlanetGateway);
    }

    @Test
    public void verifyTest(){
        Planet planet = PlanetMockBuilder.build("Alderaan");

        StarWarsPlanetPageDataContract dataContract = new StarWarsPlanetPageDataContractMockBuilder().buildNextEmpty();
        starWarsPlanetGateway.create(dataContract);
        analyzeFilms.verify(planet);

        assertThat(planet.getNumberOfAppearancesInFilms(), is(2L) );
    }

    @Test
    public void verifyTestNotExist(){
        Planet planet = PlanetMockBuilder.build("Alderaan");
        starWarsPlanetGateway.clear();
        analyzeFilms.verify(planet);

        assertThat(planet.getNumberOfAppearancesInFilms(), is(0L) );
    }

}