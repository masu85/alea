package com.alea.pokeapi.pokemon.infraestructure.persistance.ports;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonRepository;
import com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.mappers.PokemonMongoMapper;
import com.alea.pokeapi.pokemon.infraestructure.persistance.adapters.PokemonMongoAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PokemonMongoPort implements PokemonRepository {

    private final PokemonMongoMapper pokemonDataBaseMapper;
    private final PokemonMongoAdapter pokemonDataBaseAdapter;

    @Override
    public Mono<Pokemon> createPokemon(Pokemon pokemon) {
        var pokemonDBO = pokemonDataBaseMapper.pokemonToDataBase(pokemon);
        return pokemonDataBaseAdapter.save(pokemonDBO).map(pokemonDataBaseMapper::dataBaseToPokemon);
    }

    @Override
    public Flux<Pokemon> getAll() {
        return pokemonDataBaseAdapter.findAll().map(pokemonDataBaseMapper::dataBaseToPokemon);
    }
}
