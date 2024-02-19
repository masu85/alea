package com.alea.pokeapi.pokemon.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PokemonRepository {
    Mono<Pokemon> createPokemon(Pokemon pokemon);

    Flux<Pokemon> createAll(List<Pokemon> pokemonList);

    Flux<Pokemon> getAll();
}
