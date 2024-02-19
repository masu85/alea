package com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.mappers;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.PokemonMongoDBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PokemonMongoMapper {

    PokemonMongoMapper Mapper = Mappers.getMapper(PokemonMongoMapper.class);

    PokemonMongoDBO pokemonToMongo(Pokemon pokemon);
    Pokemon mongoToPokemon(PokemonMongoDBO pokemonMongoDBO);
}
