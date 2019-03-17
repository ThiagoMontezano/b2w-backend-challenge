package com.montezano.b2wstarwars.config.kafka.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Producer {

    private long sendTimeout;

    private Integer retriesProducerConfig;
}
