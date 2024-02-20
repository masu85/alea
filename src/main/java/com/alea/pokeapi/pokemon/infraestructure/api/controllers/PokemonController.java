package com.alea.pokeapi.pokemon.infraestructure.api.controllers;

import com.alea.pokeapi.pokemon.application.*;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.HeaviestPokemonDTO;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.HighestPokemonDTO;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.MoreBaseExperiencePokemonDTO;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.mappers.PokemonAPIMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/pokemon")
@RequiredArgsConstructor
public class PokemonController {

    private final GetHeaviestPokemonsUseCase getHeaviestPokemonsUseCase;
    private final GetHighestPokemonUseCase getHighestPokemonUseCase;
    private final GetMoreBaseExperiencePokemonUseCase getMoreBaseExperiencePokemonUseCase;
    private final PokemonAPIMapper pokemonAPIMapper;

    @GetMapping("/heaviest")
    public Flux<HeaviestPokemonDTO> getHeaviest() {
        return getHeaviestPokemonsUseCase.getHeaviestPokemon()
                .map(pokemonAPIMapper::pokemonToHeaviestPokemon)
                .doOnError(throwable -> log.error("Exception executing getHeaviestPokemon", throwable))
                .onErrorResume(error -> Flux.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected API error: " + error.getMessage())));
    }

    @GetMapping("/highest")
    public Flux<HighestPokemonDTO> getHigest() {
        return getHighestPokemonUseCase.getHighestPokemon()
                .map(pokemonAPIMapper::pokemonToHighestPokemon)
                .doOnError(throwable -> log.error("Exception executing getHighestPokemon", throwable))
                .onErrorResume(error -> Flux.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected API error: " + error.getMessage())));
    }

    @GetMapping("/experience")
    public Flux<MoreBaseExperiencePokemonDTO> getMoreBaseExperience() {
        return getMoreBaseExperiencePokemonUseCase.getMoreBaseExperiencePokemon()
                .map(pokemonAPIMapper::pokemonToMoreBaseExperiencePokemon)
                .doOnError(throwable -> log.error("Exception executing getMoreBaseExperiencePokemon", throwable))
                .onErrorResume(error -> Flux.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected API error: " + error.getMessage())));
    }
}