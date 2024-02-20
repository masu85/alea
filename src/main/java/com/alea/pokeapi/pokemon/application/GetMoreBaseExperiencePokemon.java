package com.alea.pokeapi.pokemon.application;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class GetMoreBaseExperiencePokemon {
    private final int NUM_MORE_BASE_EXPERIENCE_POKEMON = 5;
    private final PokemonRepository pokemonRepository;
    private final UpdatePokemonListUseCase updatePokemonListUseCase;

    public Flux<Pokemon> getMoreBaseExperience() {

        updatePokemonListUseCase.updateList();

        return pokemonRepository.getAll()
                .sort(Comparator.comparingInt(Pokemon::getBaseExperience).reversed())
                .take(NUM_MORE_BASE_EXPERIENCE_POKEMON);
    }
}
