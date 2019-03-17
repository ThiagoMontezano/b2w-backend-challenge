package com.montezano.b2wstarwars.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.montezano.b2wstarwars.http.data.StarWarsPlanetPageDataContract;

import java.io.IOException;
import java.net.URISyntaxException;

public class StarWarsPlanetPageDataContractMockBuilder {

    public StarWarsPlanetPageDataContract build() {
        try {
            return new ObjectMapper().readValue(readJson("json/star-wars-planet-data-contract.json"),
                    StarWarsPlanetPageDataContract.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StarWarsPlanetPageDataContract buildNextEmpty() {
        try {
            return new ObjectMapper().readValue(readJson("json/star-wars-planet-data-contract-next-empty.json"),
                    StarWarsPlanetPageDataContract.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readJson(final String pathJson) {
        try {
            return Resources.toString(this.getClass().getClassLoader().getResource(pathJson).toURI().toURL(),
                    Charsets.UTF_8);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
