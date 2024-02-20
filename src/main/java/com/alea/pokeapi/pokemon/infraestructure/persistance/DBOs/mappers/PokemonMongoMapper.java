package com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.mappers;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.infraestructure.persistance.DBOs.PokemonMongoDBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PokemonMongoMapper {
    PokemonMongoDBO pokemonToDataBase(Pokemon pokemon);
    Pokemon dataBaseToPokemon(PokemonMongoDBO pokemonMongoDBO);
}
