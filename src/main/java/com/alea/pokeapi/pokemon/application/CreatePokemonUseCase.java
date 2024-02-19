package com.alea.pokeapi.pokemon.application;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreatePokemonUseCase {

    private final PokemonRepository pokemonRepository;

    public Mono<Pokemon> createPokemon(Pokemon pokemon) {
        return pokemonRepository.createPokemon(pokemon);
    }
}
