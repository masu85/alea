package com.alea.pokeapi.pokemon.infraestructure.persistance.ports;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.domain.PokemonRepository;
import com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.mappers.PokemonMongoMapper;
import com.alea.pokeapi.pokemon.infraestructure.persistance.adapters.PokemonMongoAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(value = "pokeapi.database-port", havingValue = "mongo")
public class PokemonMongoPort implements PokemonRepository {

    private final PokemonMongoMapper pokemonDataBaseMapper;
    private final PokemonMongoAdapter pokemonDataBaseAdapter;

    @Override
    public Mono<Pokemon> createPokemon(Pokemon pokemon) {
        //System.out.println(pokemon.getName());
        var pokemonDBO = pokemonDataBaseMapper.pokemonToDataBase(pokemon);
        return pokemonDataBaseAdapter.save(pokemonDBO).map(pokemonDataBaseMapper::dataBaseToPokemon);
    }

    public Flux<Pokemon> createAll(List<Pokemon> pokemonList) {
        return pokemonDataBaseAdapter.saveAll(pokemonList.stream().map(pokemonDataBaseMapper::pokemonToDataBase).toList())
                .map(pokemonDataBaseMapper::dataBaseToPokemon);
    }

    @Override
    public Flux<Pokemon> getAll() {
        return pokemonDataBaseAdapter.findAll().map(pokemonDataBaseMapper::dataBaseToPokemon);
    }

    @Override
    public Mono<Boolean> existById(Integer id) {
        return pokemonDataBaseAdapter.existsById(String.valueOf(id));
    }
}
