package com.montezano.b2wstarwars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(
        basePackages = {"com.montezano.b2wstarwars.gateways.repository"}
)
public class B2wStarWarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(B2wStarWarsApplication.class, args);
	}

}
