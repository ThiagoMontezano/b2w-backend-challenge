package com.montezano.b2wstarwars.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;


@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Planet {

	@Id
	private String id;

	@NotBlank
	private String name;

	@NotBlank
	private String climate;

	@NotBlank
	private String terrain;

	private long numberOfAppearancesInFilms;
}
