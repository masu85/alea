package com.alea.pokeapi.pokemon.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public interface PokemonExternalData {
    Mono<Integer> countPokemon();
    Flux<Tuple2<String, String>> getExistingPokemon(Integer offset, Integer limit);
}
