package com.montezano.b2wstarwars.http.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
public class PlanetDataContract {

    @NotBlank
    private String name;

    @NotBlank
    private String climate;

    @NotBlank
    private String terrain;
}
