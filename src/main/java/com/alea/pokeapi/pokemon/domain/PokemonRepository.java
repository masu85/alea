package com.alea.pokeapi.pokemon.domain;

import reactor.core.publisher.Mono;

public interface PokemonRepository {

    Mono<Pokemon> createPokemon(Pokemon pokemon);
}
