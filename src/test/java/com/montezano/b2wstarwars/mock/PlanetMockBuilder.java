package com.montezano.b2wstarwars.mock;

import com.montezano.b2wstarwars.domains.Planet;

import java.util.UUID;

public class PlanetMockBuilder {

    public static Planet build(final String name) {
        final Planet planet = new Planet();
        planet.setName(name);
        planet.setClimate("climate");
        planet.setTerrain("terrain");
        planet.setNumberOfAppearancesInFilms(1L);
        planet.setId(UUID.randomUUID().toString());
        return planet;
    }
}
