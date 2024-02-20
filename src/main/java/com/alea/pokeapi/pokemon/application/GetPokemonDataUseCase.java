package com.alea.pokeapi.pokemon.application;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonExternalData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetPokemonDataUseCase {

    private final PokemonExternalData pokemonExternalData;

    /*public Flux<Pokemon> getExternalData(Integer offset, Integer limit) {
         return pokemonExternalData.getExistingPokemon(offset, limit);
    }*/
}
