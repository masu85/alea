package com.alea.pokeapi.pokemon.application;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonExternalData;
import com.alea.pokeapi.pokemon.domain.PokemonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePokemonListUseCaseTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private PokemonExternalData pokemonExternalData;

    @InjectMocks
    private UpdatePokemonListUseCase updatePokemonListUseCase;

    @Test
    public void updatePokemonList_init_event_call_ok() {

        when(pokemonRepository.getAll()).thenReturn(Flux.empty());
        when(pokemonExternalData.countPokemon()).thenReturn(Mono.just(0));
        when(pokemonExternalData.getExistingPokemons(0, 0)).thenReturn(Flux.empty());

        updatePokemonListUseCase.init();

        verify(pokemonRepository).getAll();
        verify(pokemonExternalData).countPokemon();
    }

    @Test
    public void updatePokemonList_no_DDBB_pokemon() {

        var pk1 = Pokemon.builder().id(1).name("a").weight(1).build();
        var pk2 = Pokemon.builder().id(2).name("b").weight(2).build();
        var pk3 = Pokemon.builder().id(3).name("c").weight(3).build();

        var tuple1 = Tuples.of("a", "url1");
        var tuple2 = Tuples.of("b", "url2");
        var tuple3 = Tuples.of("c", "url3");

        when(pokemonExternalData.countPokemon()).thenReturn(Mono.just(3));
        when(pokemonExternalData.getExistingPokemons(0, 3)).thenReturn(Flux.just(tuple1, tuple2, tuple3));
        when(pokemonRepository.getAll()).thenReturn(Flux.empty());

        when(pokemonExternalData.getPokemon(tuple1.getT2())).thenReturn(Mono.just(pk1));
        when(pokemonExternalData.getPokemon(tuple2.getT2())).thenReturn(Mono.just(pk2));
        when(pokemonExternalData.getPokemon(tuple3.getT2())).thenReturn(Mono.just(pk3));

        when(pokemonRepository.createPokemon(pk1)).thenReturn(Mono.just(pk1));
        when(pokemonRepository.createPokemon(pk2)).thenReturn(Mono.just(pk2));
        when(pokemonRepository.createPokemon(pk3)).thenReturn(Mono.just(pk3));


        updatePokemonListUseCase.updateList().blockLast();

        verify(pokemonExternalData).countPokemon();
        verify(pokemonExternalData).getExistingPokemons(0, 3);
        verify(pokemonRepository).getAll();
        verify(pokemonExternalData, times(3)).getPokemon(any());
        verify(pokemonRepository, times(3)).createPokemon(any());
    }

    @Test
    public void updatePokemonList_already_inserted_DDBB_pokemon() {

        var pk1 = Pokemon.builder().id(1).name("a").weight(1).build();
        var pk2 = Pokemon.builder().id(2).name("b").weight(2).build();
        var pk3 = Pokemon.builder().id(3).name("c").weight(3).build();

        var tuple1 = Tuples.of("a", "url1");
        var tuple2 = Tuples.of("b", "url2");
        var tuple3 = Tuples.of("c", "url3");

        when(pokemonExternalData.countPokemon()).thenReturn(Mono.just(3));
        when(pokemonExternalData.getExistingPokemons(0, 3)).thenReturn(Flux.just(tuple1, tuple2, tuple3));
        when(pokemonRepository.getAll()).thenReturn(Flux.just(pk1, pk2, pk3));

        updatePokemonListUseCase.updateList().blockLast();

        verify(pokemonExternalData).countPokemon();
        verify(pokemonExternalData).getExistingPokemons(0, 3);
        verify(pokemonRepository).getAll();
        verify(pokemonExternalData, times(0)).getPokemon(any());
        verify(pokemonRepository, times(0)).createPokemon(any());
    }

    @Test
    public void updatePokemonList_only_some_inserted_DDBB_pokemon() {

        var pk1 = Pokemon.builder().id(1).name("a").weight(1).build();
        var pk2 = Pokemon.builder().id(2).name("b").weight(2).build();
        var pk3 = Pokemon.builder().id(3).name("c").weight(3).build();

        var tuple1 = Tuples.of("a", "url1");
        var tuple2 = Tuples.of("b", "url2");
        var tuple3 = Tuples.of("c", "url3");

        when(pokemonExternalData.countPokemon()).thenReturn(Mono.just(3));
        when(pokemonExternalData.getExistingPokemons(0, 3)).thenReturn(Flux.just(tuple1, tuple2, tuple3));
        when(pokemonRepository.getAll()).thenReturn(Flux.just(pk1));

        when(pokemonExternalData.getPokemon(tuple2.getT2())).thenReturn(Mono.just(pk2));
        when(pokemonExternalData.getPokemon(tuple3.getT2())).thenReturn(Mono.just(pk3));

        when(pokemonRepository.createPokemon(pk2)).thenReturn(Mono.just(pk2));
        when(pokemonRepository.createPokemon(pk3)).thenReturn(Mono.just(pk3));

        updatePokemonListUseCase.updateList().blockLast();

        verify(pokemonExternalData).countPokemon();
        verify(pokemonExternalData).getExistingPokemons(0, 3);
        verify(pokemonRepository).getAll();
        verify(pokemonExternalData, times(2)).getPokemon(any());
        verify(pokemonRepository, times(2)).createPokemon(any());
    }
}