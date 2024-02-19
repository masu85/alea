package com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.mappers;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.PokemonPokeApiDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PokemonPokeApiClientMapper {
    PokemonPokeApiClientMapper Mapper = Mappers.getMapper(PokemonPokeApiClientMapper.class);

    PokemonPokeApiDTO pokemonToPokeApi(Pokemon pokemon);
    Pokemon pokeApiToPokemon(PokemonPokeApiDTO pokemonPokeApiDTO);
}