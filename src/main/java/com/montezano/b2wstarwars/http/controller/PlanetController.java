package com.montezano.b2wstarwars.http.controller;

import com.montezano.b2wstarwars.domains.Planet;
import com.montezano.b2wstarwars.http.data.StarWarsPlanetPageDataContract;
import com.montezano.b2wstarwars.http.UrlMapping;
import com.montezano.b2wstarwars.http.converter.PlanetConverter;
import com.montezano.b2wstarwars.http.data.PlanetDataContract;
import com.montezano.b2wstarwars.usecases.PlanetManager;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.PLANET)
public class PlanetController {

    private final PlanetManager planetManager;

    private final PlanetConverter planetConverter;

    private static Mono<? extends ResponseEntity<Void>> apply(final Boolean m) {
        return m ? Mono.just(new ResponseEntity<>(HttpStatus.OK)) :
                Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Create planet")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    @PostMapping
    public Mono<Planet> create(@Valid @RequestBody PlanetDataContract planetDataContract){
        log.info("Create -> {}", planetDataContract);
        return planetManager.save(planetConverter.convert(planetDataContract));
    }

    @ApiOperation(value = "Find all planets")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Planets found"),
    })
    @GetMapping(produces = "application/stream+json")
    public Flux<Planet> getAll(){
        return planetManager.getAllPlanets();
    }

    @ApiOperation(value = "Find planet by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Find successfully"),
            @ApiResponse(code = 400, message = "Planet Not found")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Planet>> getPlanet(@PathVariable(value = "id") String id) {
        return planetManager.findById(id)
                .map(savedPlanet -> ResponseEntity.ok(savedPlanet))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Find planet by Name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Find successfully"),
            @ApiResponse(code = 400, message = "Planet Not found")
    })
    @GetMapping("/name/{name}")
    public Mono<ResponseEntity<Planet>> getPlanetByName(@PathVariable String name) {
        return planetManager.findByName(name)
                .map(savedPlanet -> ResponseEntity.ok(savedPlanet))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Delete planet by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete successfully"),
            @ApiResponse(code = 400, message = "Planet Not found")
    })
    @DeleteMapping(value="/{id}")
    public Mono<ResponseEntity<Void>> deletePlanet(@PathVariable final String id){
        log.info("Delete by id -> {}", id);

        return planetManager.deleteById(id)
                .flatMap(PlanetController::apply);
    }

    @ApiOperation(value = "Delete planet by Name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete successfully"),
            @ApiResponse(code = 400, message = "Planet Not found")
    })
    @DeleteMapping(value="/name/{name}")
    public Mono<ResponseEntity<Void>> deletePlanetByName(@PathVariable final String name){
        log.info("Delete by name -> {}", name);
        return planetManager.deleteByName(name)
                .flatMap(PlanetController::apply);
    }

    @ApiOperation(value = "Find all star wars planets")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Planets found"),
    })
    @GetMapping(value = "/starWars")
    public Flux<StarWarsPlanetPageDataContract> getAllStarWarsPlanets(@RequestParam(value="page") final Integer page){
        return planetManager.getAllStarWarsPlanets(page);
    }

}
