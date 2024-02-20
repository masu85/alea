package com.alea.pokeapi.pokemon.application;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonExternalData;
import com.alea.pokeapi.pokemon.domain.PokemonRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ParallelFlux;

@Service
@RequiredArgsConstructor
public class UpdatePokemonListUseCase {

    private final PokemonRepository pokemonRepository;
    private final PokemonExternalData pokemonExternalData;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        //First populate on Startup
        updateList()
                .doOnComplete(() -> System.out.println("Carga inicial finalizada"))
                .subscribe();
    }

    public ParallelFlux<Pokemon> updateList() {

        var offset = 0;
        var pokemonExternalFlux = pokemonExternalData.countPokemon()
                .flatMapMany(numPokemons -> pokemonExternalData.getExistingPokemons(offset, numPokemons));

        var pokemonDatabaseFlux = pokemonRepository.getAll();

        return pokemonDatabaseFlux
                .collectList()
                .flatMapMany(pokemonDataBaseList ->
                    pokemonExternalFlux
                            .filter(pokemonExternal ->
                                    pokemonDataBaseList.stream().noneMatch(pokemon ->
                                            pokemon.getName().equals(pokemonExternal.getT1())
                                    )
                            )
                            .flatMap(pokemonExternal ->
                                    pokemonExternalData.getPokemon(pokemonExternal.getT2())
                                            .flatMap(pokemonRepository::createPokemon)
                            )
                )
                .parallel();
    }
}
