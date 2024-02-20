package com.alea.pokeapi.pokemon.infraestructure.api.controllers;

import com.alea.pokeapi.pokemon.application.*;
import com.alea.pokeapi.pokemon.infraestructure.clients.PokeApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/pokemon")
@RequiredArgsConstructor
public class PokemonController {

    private final PokeApiClient pokeApiClient;
    private final CreatePokemonUseCase createPokemonUseCase;
    private final GetHeaviestPokemonsUseCase getHeaviestPokemonsUseCase;
    private final GetHighestPokemonUseCase getHighestPokemonUseCase;
    private final GetMoreBaseExperiencePokemon getMoreBaseExperiencePokemon;
    private final UpdatePokemonListUseCase updatePokemonListUseCase;
    private final Lock updateLock;

    @GetMapping
    public void test() {
        Instant start = Instant.now();
        //System.out.println(pokeApiClient.countPokemon().block());
        //System.out.println(pokeApiClient.getPokemon(6).flatMap(createPokemonUseCase::createPokemon).block());

        //updatePokemonListUseCase.updateList();

        getHeaviestPokemonsUseCase.getHeaviestPokemon().doOnNext(pokemon -> System.out.println(pokemon.getName() + " - " + pokemon.getWeight())).blockLast();
        getHighestPokemonUseCase.getHighest().doOnNext(pokemon -> System.out.println(pokemon.getName() + " - " + pokemon.getHeight())).blockLast();
        getMoreBaseExperiencePokemon.getMoreBaseExperience().doOnNext(pokemon -> System.out.println(pokemon.getName() + " - " + pokemon.getBaseExperience())).blockLast();
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }
}