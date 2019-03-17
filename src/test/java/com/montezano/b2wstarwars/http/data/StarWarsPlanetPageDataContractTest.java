package com.montezano.b2wstarwars.http.data;

import com.montezano.b2wstarwars.mock.StarWarsPlanetPageDataContractMockBuilder;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StarWarsPlanetPageDataContractTest {

    @Test
    public void hasMore(){
        StarWarsPlanetPageDataContractMockBuilder builder = new StarWarsPlanetPageDataContractMockBuilder();
        StarWarsPlanetPageDataContract dataContract = builder.build();
        StarWarsPlanetPageDataContract dataContractNext = builder.buildNextEmpty();
        assertThat(dataContract.hasMore(), is(Boolean.TRUE));
        assertThat(dataContractNext.hasMore(), is(Boolean.FALSE));
    }
}