package com.alea.pokeapi.pokemon.application;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class GetHighestPokemonUseCase {
    private final int NUM_HIGHEST_POKEMON = 5;
    private final PokemonRepository pokemonRepository;
    private final UpdatePokemonListUseCase updatePokemonListUseCase;

    public Flux<Pokemon> getHighestPokemon() {

        return updatePokemonListUseCase.updateList()
                .thenMany(pokemonRepository.getAll()
                        .sort(Comparator.comparingInt(Pokemon::getHeight).reversed())
                        .take(NUM_HIGHEST_POKEMON));
    }
}
