package com.montezano.b2wstarwars.http.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.montezano.b2wstarwars.domains.Planet;
import com.montezano.b2wstarwars.http.UrlMapping;
import com.montezano.b2wstarwars.http.converter.PlanetConverter;
import com.montezano.b2wstarwars.http.data.PlanetDataContract;
import com.montezano.b2wstarwars.http.data.StarWarsPlanetPageDataContract;
import com.montezano.b2wstarwars.mock.StarWarsPlanetPageDataContractMockBuilder;
import com.montezano.b2wstarwars.usecases.PlanetManager;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RunWith(SpringRunner.class)
@WebFluxTest(value = {
        PlanetController.class
})
public class PlanetControllerTest {

    @MockBean
    private PlanetManager planetManager;

    @MockBean
    private PlanetConverter planetConverter;

    @SpyBean
    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient webTestClient;

    private static final String CONTENT_TYPE_STRING = "content-type";

    private static Map<String, Planet> planetMap = new HashMap<>();

    @BeforeClass
    public static void setup() throws Exception {
        planetMap.put("Dagobah", new Planet("1", "Dagobah", "murky", "swamp, jungles",3L));
        planetMap.put("Hoth", new Planet("2", "Hoth", "frozen", "tundra, ice caves, mountain ranges",1L));
    }

    @Test
    public void createSuccessfully() throws JsonProcessingException {
        webTestClient.post()
                .uri(UrlMapping.PLANET)
                .header(CONTENT_TYPE_STRING, APPLICATION_JSON_UTF8_VALUE)
                .syncBody(objectMapper.writeValueAsString(fixturePlanetDataContract()))
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        verify(planetConverter, times(1)).convert(any(PlanetDataContract.class));
        verify(planetManager, times(1)).save(any());
    }

    @Test
    public void getAll(){
        BDDMockito.given(this.planetManager.getAllPlanets())
                .willReturn(Flux.just(planetMap.get("Dagobah"),planetMap.get("Hoth")));

        webTestClient.get()
                .uri(UrlMapping.PLANET)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Planet.class)
                .hasSize(2);

        verify(planetManager, times(1)).getAllPlanets();
    }

    @Test
    public void getId(){
        BDDMockito.given(this.planetManager.findById("1"))
                .willReturn(Mono.just(planetMap.get("Dagobah")));

        webTestClient.get()
                .uri(UrlMapping.PLANET+"/1")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Planet.class)
                .hasSize(1);

        verify(planetManager, times(1)).findById("1");
    }

    @Test
    public void getId_NotFound(){
        BDDMockito.given(this.planetManager.findById("1"))
                .willReturn(Mono.empty());

        webTestClient.get()
                .uri(UrlMapping.PLANET+"/1")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBodyList(Planet.class)
                .hasSize(0);

        verify(planetManager, times(1)).findById("1");
    }

    @Test
    public void getByName(){
        BDDMockito.given(this.planetManager.findByName("Dagobah"))
                .willReturn(Mono.just(planetMap.get("Dagobah")));

        webTestClient.get()
                .uri(UrlMapping.PLANET+"/name/Dagobah")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Planet.class)
                .hasSize(1);

        verify(planetManager, times(1)).findByName("Dagobah");
    }

    @Test
    public void getByName_NotFound(){
        BDDMockito.given(this.planetManager.findByName("Dagobah"))
                .willReturn(Mono.empty());

        webTestClient.get()
                .uri(UrlMapping.PLANET+"/name/Dagobah")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBodyList(Planet.class)
                .hasSize(0);

        verify(planetManager, times(1)).findByName("Dagobah");
    }

    @Test
    public void deletePlanetSuccessfully(){
        BDDMockito.given(this.planetManager.deleteById("1"))
                .willReturn(Mono.just(Boolean.TRUE));

        webTestClient.delete()
                .uri(UrlMapping.PLANET+"/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);

        verify(planetManager, times(1)).deleteById("1");
    }

    @Test
    public void deletePlanetFail(){
        BDDMockito.given(this.planetManager.deleteById("1"))
                .willReturn(Mono.just(Boolean.FALSE));

        webTestClient.delete()
                .uri(UrlMapping.PLANET+"/{id}", 1)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class);

        verify(planetManager, times(1)).deleteById("1");
    }

    @Test
    public void getAllStarWarsPlanets(){
        StarWarsPlanetPageDataContract dataContract = fixtureStarWarsPlanet();

        BDDMockito.given(this.planetManager.getAllStarWarsPlanets(1))
                .willReturn(Flux.just(dataContract));

        webTestClient.get()
                .uri(UrlMapping.PLANET+"/starWars?page=1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StarWarsPlanetPageDataContract.class)
                .hasSize(1);

        verify(planetManager, times(1)).getAllStarWarsPlanets(1);
    }

    private StarWarsPlanetPageDataContract fixtureStarWarsPlanet() {
        return new StarWarsPlanetPageDataContractMockBuilder().build();
    }

    private PlanetDataContract fixturePlanetDataContract(){
        final PlanetDataContract dataContract = new PlanetDataContract();
        dataContract.setName("name");
        dataContract.setClimate("climate");
        dataContract.setTerrain("terrain");
        return dataContract;
    }
}