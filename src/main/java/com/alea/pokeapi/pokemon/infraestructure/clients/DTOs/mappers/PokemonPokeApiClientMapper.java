package com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.mappers;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.infraestructure.clients.DTOs.PokemonDetailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PokemonPokeApiClientMapper {
    PokemonDetailDTO pokemonToPokeApi(Pokemon pokemon);
    Pokemon pokeApiToPokemon(PokemonDetailDTO pokemonPokeApiDTO);
}