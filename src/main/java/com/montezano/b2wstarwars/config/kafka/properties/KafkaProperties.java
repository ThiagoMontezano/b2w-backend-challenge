package com.montezano.b2wstarwars.config.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties(prefix = "integration.kafka")
@Getter
@Setter
@Profile("!test")
public class KafkaProperties {

    private String serversConfig;

    private String groupId;

    private Producer producer;

    private Consumer consumer;
}

