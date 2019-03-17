package com.montezano.b2wstarwars.http.converter;

import com.montezano.b2wstarwars.domains.Planet;
import com.montezano.b2wstarwars.http.data.PlanetDataContract;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
@RequiredArgsConstructor
public class PlanetConverter implements Converter<PlanetDataContract, Planet>{

    @Override
    public Planet convert(final PlanetDataContract planetDataContract) {
        return copy(planetDataContract, new Planet());
    }

    private <I,O> O copy(I source, O destination){
        copyProperties(source, destination);
        return destination;
    }
}
