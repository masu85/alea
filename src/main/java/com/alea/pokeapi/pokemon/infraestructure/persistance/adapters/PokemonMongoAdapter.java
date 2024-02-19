package com.alea.pokeapi.pokemon.infraestructure.persistance.adapters;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.mappers.PokemonMongoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(value = "pokeapi.database-adapter", havingValue = "mongo")
public class PokemonMongoAdapter implements PokemonDataBaseAdapter {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<Pokemon> save(Pokemon pokemon) {
        var pokemonDBO = PokemonMongoMapper.Mapper.pokemonToMongo(pokemon);
        return reactiveMongoTemplate.save(pokemonDBO).map(PokemonMongoMapper.Mapper::mongoToPokemon);
    }
}
