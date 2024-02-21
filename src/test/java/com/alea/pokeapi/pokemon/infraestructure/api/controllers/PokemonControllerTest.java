package com.alea.pokeapi.pokemon.infraestructure.api.controllers;

import com.alea.pokeapi.pokemon.application.GetHeaviestPokemonsUseCase;
import com.alea.pokeapi.pokemon.application.GetHighestPokemonUseCase;
import com.alea.pokeapi.pokemon.application.GetMoreBaseExperiencePokemonUseCase;
import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.HeaviestPokemonDTO;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.HighestPokemonDTO;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.MoreBaseExperiencePokemonDTO;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.mappers.PokemonAPIMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonControllerTest {

    @Mock
    private GetHeaviestPokemonsUseCase getHeaviestPokemonsUseCase;
    @Mock
    private GetHighestPokemonUseCase getHighestPokemonUseCase;
    @Mock
    private GetMoreBaseExperiencePokemonUseCase getMoreBaseExperiencePokemonUseCase;
    @Mock
    private PokemonAPIMapper pokemonAPIMapper;

    @InjectMocks
    private PokemonController pokemonController;

    @Test
    public void callGetHeaviestPokemonUseCaseFromController() {

        var pk1 = Pokemon.builder().id(1).name("a").weight(1).build();
        var pk2 = Pokemon.builder().id(2).name("b").weight(2).build();
        var pk3 = Pokemon.builder().id(3).name("c").weight(3).build();
        var pk4 = Pokemon.builder().id(4).name("d").weight(4).build();
        var pk5 = Pokemon.builder().id(5).name("e").weight(5).build();

        var ph1 = HeaviestPokemonDTO.builder().id(1).name("a").weight(1).build();
        var ph2 = HeaviestPokemonDTO.builder().id(2).name("b").weight(2).build();
        var ph3 = HeaviestPokemonDTO.builder().id(3).name("c").weight(3).build();
        var ph4 = HeaviestPokemonDTO.builder().id(4).name("d").weight(4).build();
        var ph5 = HeaviestPokemonDTO.builder().id(5).name("e").weight(5).build();


        when(getHeaviestPokemonsUseCase.getHeaviestPokemon()).thenReturn(Flux.just(pk1, pk2, pk3, pk4, pk5));
        when(pokemonAPIMapper.pokemonToHeaviestPokemon(pk1)).thenReturn(ph1);
        when(pokemonAPIMapper.pokemonToHeaviestPokemon(pk2)).thenReturn(ph2);
        when(pokemonAPIMapper.pokemonToHeaviestPokemon(pk3)).thenReturn(ph3);
        when(pokemonAPIMapper.pokemonToHeaviestPokemon(pk4)).thenReturn(ph4);
        when(pokemonAPIMapper.pokemonToHeaviestPokemon(pk5)).thenReturn(ph5);

        pokemonController.getHeaviest().blockLast();

        verify(getHeaviestPokemonsUseCase).getHeaviestPokemon();
        verify(pokemonAPIMapper, times(5)).pokemonToHeaviestPokemon(any());
    }

    @Test
    public void callGetHighestPokemonUseCaseFromController() {

        var pk1 = Pokemon.builder().id(1).name("a").height(1).build();
        var pk2 = Pokemon.builder().id(2).name("b").height(2).build();
        var pk3 = Pokemon.builder().id(3).name("c").height(3).build();
        var pk4 = Pokemon.builder().id(4).name("d").height(4).build();
        var pk5 = Pokemon.builder().id(5).name("e").height(5).build();

        var ph1 = HighestPokemonDTO.builder().id(1).name("a").height(1).build();
        var ph2 = HighestPokemonDTO.builder().id(2).name("b").height(2).build();
        var ph3 = HighestPokemonDTO.builder().id(3).name("c").height(3).build();
        var ph4 = HighestPokemonDTO.builder().id(4).name("d").height(4).build();
        var ph5 = HighestPokemonDTO.builder().id(5).name("e").height(5).build();


        when(getHighestPokemonUseCase.getHighestPokemon()).thenReturn(Flux.just(pk1, pk2, pk3, pk4, pk5));
        when(pokemonAPIMapper.pokemonToHighestPokemon(pk1)).thenReturn(ph1);
        when(pokemonAPIMapper.pokemonToHighestPokemon(pk2)).thenReturn(ph2);
        when(pokemonAPIMapper.pokemonToHighestPokemon(pk3)).thenReturn(ph3);
        when(pokemonAPIMapper.pokemonToHighestPokemon(pk4)).thenReturn(ph4);
        when(pokemonAPIMapper.pokemonToHighestPokemon(pk5)).thenReturn(ph5);

        pokemonController.getHigest().blockLast();

        verify(getHighestPokemonUseCase).getHighestPokemon();
        verify(pokemonAPIMapper, times(5)).pokemonToHighestPokemon(any());
    }

    @Test
    public void callGetMoreBaseExperiencePokemonUseCaseFromController() {

        var pk1 = Pokemon.builder().id(1).name("a").baseExperience(1).build();
        var pk2 = Pokemon.builder().id(2).name("b").baseExperience(2).build();
        var pk3 = Pokemon.builder().id(3).name("c").baseExperience(3).build();
        var pk4 = Pokemon.builder().id(4).name("d").baseExperience(4).build();
        var pk5 = Pokemon.builder().id(5).name("e").baseExperience(5).build();

        var ph1 = MoreBaseExperiencePokemonDTO.builder().id(1).name("a").baseExperience(1).build();
        var ph2 = MoreBaseExperiencePokemonDTO.builder().id(2).name("b").baseExperience(2).build();
        var ph3 = MoreBaseExperiencePokemonDTO.builder().id(3).name("c").baseExperience(3).build();
        var ph4 = MoreBaseExperiencePokemonDTO.builder().id(4).name("d").baseExperience(4).build();
        var ph5 = MoreBaseExperiencePokemonDTO.builder().id(5).name("e").baseExperience(5).build();


        when(getMoreBaseExperiencePokemonUseCase.getMoreBaseExperiencePokemon()).thenReturn(Flux.just(pk1, pk2, pk3, pk4, pk5));
        when(pokemonAPIMapper.pokemonToMoreBaseExperiencePokemon(pk1)).thenReturn(ph1);
        when(pokemonAPIMapper.pokemonToMoreBaseExperiencePokemon(pk2)).thenReturn(ph2);
        when(pokemonAPIMapper.pokemonToMoreBaseExperiencePokemon(pk3)).thenReturn(ph3);
        when(pokemonAPIMapper.pokemonToMoreBaseExperiencePokemon(pk4)).thenReturn(ph4);
        when(pokemonAPIMapper.pokemonToMoreBaseExperiencePokemon(pk5)).thenReturn(ph5);

        pokemonController.getMoreBaseExperience().blockLast();

        verify(getMoreBaseExperiencePokemonUseCase).getMoreBaseExperiencePokemon();
        verify(pokemonAPIMapper, times(5)).pokemonToMoreBaseExperiencePokemon(any());
    }

}