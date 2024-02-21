package com.alea.pokeapi.pokemon.infraestructure.persistance.ports;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.PokemonMongoDBO;
import com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.mappers.PokemonMongoMapper;
import com.alea.pokeapi.pokemon.infraestructure.persistance.adapters.PokemonMongoAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PokemonMongoPortTest {

    @Mock
    private PokemonMongoMapper pokemonDataBaseMapper;
    @Mock
    private PokemonMongoAdapter pokemonDataBaseAdapter;

    @InjectMocks
    private PokemonMongoPort pokemonMongoPort;

    @Test
    public void createPokemonOk() {
        var pk1 = Pokemon.builder().id(1).name("a").weight(1).build();
        var ph1 = PokemonMongoDBO.builder().id(1).name("a").weight(1).build();

        when(pokemonDataBaseMapper.pokemonToDataBase(pk1)).thenReturn(ph1);
        when(pokemonDataBaseAdapter.save(ph1)).thenReturn(Mono.just(ph1));
        when(pokemonDataBaseMapper.dataBaseToPokemon(ph1)).thenReturn(pk1);

        pokemonMongoPort.createPokemon(pk1).block();

        verify(pokemonDataBaseMapper).pokemonToDataBase(pk1);
        verify(pokemonDataBaseAdapter).save(ph1);
        verify(pokemonDataBaseMapper).dataBaseToPokemon(ph1);
    }

    @Test
    public void getAllOk() {
        var pk1 = Pokemon.builder().id(1).name("a").weight(1).build();
        var ph1 = PokemonMongoDBO.builder().id(1).name("a").weight(1).build();

        when(pokemonDataBaseAdapter.findAll()).thenReturn(Flux.just(ph1));
        when(pokemonDataBaseMapper.dataBaseToPokemon(ph1)).thenReturn(pk1);

        pokemonMongoPort.getAll().blockLast();

        verify(pokemonDataBaseAdapter).findAll();
        verify(pokemonDataBaseMapper).dataBaseToPokemon(ph1);
    }
}