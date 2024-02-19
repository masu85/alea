package com.alea.pokeapi.pokemon.infraestructure.persistance.adapters;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import reactor.core.publisher.Mono;

public interface PokemonDataBaseAdapter {
    Mono<Pokemon> save(Pokemon pokemon);
}
