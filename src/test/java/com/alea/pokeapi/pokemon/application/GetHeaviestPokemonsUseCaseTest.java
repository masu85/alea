package com.alea.pokeapi.pokemon.application;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetHeaviestPokemonsUseCaseTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private UpdatePokemonListUseCase updatePokemonListUseCase;

    @InjectMocks
    private GetHeaviestPokemonsUseCase getHeaviestPokemonsUseCase;

    @Test
    public void getHeaviestPokemon_calls_update_and_getAll_ok() {

        var pk1 = Pokemon.builder().id(1).name("a").weight(1).build();
        var pk2 = Pokemon.builder().id(2).name("b").weight(2).build();
        var pk3 = Pokemon.builder().id(3).name("c").weight(3).build();
        var pk4 = Pokemon.builder().id(4).name("d").weight(4).build();
        var pk5 = Pokemon.builder().id(5).name("e").weight(5).build();
        var pk6 = Pokemon.builder().id(6).name("f").weight(6).build();
        var pk7 = Pokemon.builder().id(7).name("g").weight(7).build();

        when(updatePokemonListUseCase.updateList()).thenReturn(Flux.empty());
        when(pokemonRepository.getAll()).thenReturn(Flux.just(pk1, pk2, pk3, pk4, pk5, pk6, pk7));

        var results = getHeaviestPokemonsUseCase.getHeaviestPokemon().collectList().block();

        verify(updatePokemonListUseCase).updateList();
        verify(pokemonRepository).getAll();

        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(5);
        assertThat(results.get(0).getId()).isEqualTo(7);
        assertThat(results.get(1).getId()).isEqualTo(6);
        assertThat(results.get(2).getId()).isEqualTo(5);
        assertThat(results.get(3).getId()).isEqualTo(4);
        assertThat(results.get(4).getId()).isEqualTo(3);
    }
}