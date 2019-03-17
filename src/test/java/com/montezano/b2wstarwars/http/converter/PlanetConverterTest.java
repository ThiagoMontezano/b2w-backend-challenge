package com.montezano.b2wstarwars.http.converter;

import com.montezano.b2wstarwars.domains.Planet;
import com.montezano.b2wstarwars.http.data.PlanetDataContract;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class PlanetConverterTest {

    @Test
    public void convert(){
        PlanetConverter converter = new PlanetConverter();
        PlanetDataContract dataContract = fixturePlanetDataContract();
        Planet planet = converter.convert(dataContract);

        assertNotNull(dataContract);
        assertNotNull(planet);
        assertThat(dataContract.getClimate(), is(planet.getClimate()));
        assertThat(dataContract.getName(), is(planet.getName()));
        assertThat(dataContract.getTerrain(), is(planet.getTerrain()));
    }

    private PlanetDataContract fixturePlanetDataContract() {
        final PlanetDataContract dataContract = new PlanetDataContract();
        dataContract.setName("name");
        dataContract.setClimate("climate");
        dataContract.setTerrain("terrain");
        return dataContract;
    }

}