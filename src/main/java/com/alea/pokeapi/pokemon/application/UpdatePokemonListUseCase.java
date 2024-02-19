package com.alea.pokeapi.pokemon.application;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonExternalData;
import com.alea.pokeapi.pokemon.domain.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdatePokemonListUseCase {

    private final PokemonRepository pokemonRepository;
    private final PokemonExternalData pokemonExternalData;

    private final GetPokemonDataUseCase getPokemonDataUseCase;

    public Flux<String> updateList() {

        var offset = 0;

        Flux<Tuple2<String, String>> pokemonExternalFlux = pokemonExternalData.countPokemon()
                .flatMapMany(numPokemons -> pokemonExternalData.getExistingPokemon(offset, numPokemons));

        Flux<Pokemon> pokemonDatabaseFlux = pokemonRepository.getAll();

        return pokemonExternalFlux.filterWhen(tuple ->
            pokemonDatabaseFlux.filter(pokemon -> pokemon.getName().equals(tuple.getT1()))
                    .hasElements()
                    .map(hasElements -> !hasElements)
        )
        .map(tupla2 -> {
            System.out.println(tupla2.getT2());
            return tupla2.getT2();
        });
    }
}
