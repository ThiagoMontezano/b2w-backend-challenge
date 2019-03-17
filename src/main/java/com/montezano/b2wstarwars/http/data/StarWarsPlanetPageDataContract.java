package com.montezano.b2wstarwars.http.data;

import java.io.Serializable;
import java.util.List;

public class StarWarsPlanetPageDataContract implements Serializable {
    public int count;
    public String next;
    public String previous;
    public List<PlanetStarWarsDataContract> results;

    public boolean hasMore() {
        return (next != null && !next.isEmpty());
    }
}

