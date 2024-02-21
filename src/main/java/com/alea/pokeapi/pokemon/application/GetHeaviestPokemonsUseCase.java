package com.alea.pokeapi.pokemon.application;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class GetHeaviestPokemonsUseCase {
    private final int NUM_HEAVIEST_POKEMON = 5;
    private final PokemonRepository pokemonRepository;
    private final UpdatePokemonListUseCase updatePokemonListUseCase;

    public Flux<Pokemon> getHeaviestPokemon() {

        return updatePokemonListUseCase.updateList()
                .thenMany(pokemonRepository.getAll()
                        .sort(Comparator.comparingInt(Pokemon::getWeight).reversed())
                        .take(NUM_HEAVIEST_POKEMON));
    }
}
