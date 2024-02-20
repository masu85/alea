package com.alea.pokeapi.pokemon.infraestructure.api.DTOs.mappers;

import com.alea.pokeapi.pokemon.domain.Pokemon;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.HeaviestPokemonDTO;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.HighestPokemonDTO;
import com.alea.pokeapi.pokemon.infraestructure.api.DTOs.MoreBaseExperiencePokemonDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PokemonAPIMapper {
    HeaviestPokemonDTO pokemonToHeaviestPokemon(Pokemon pokemon);
    HighestPokemonDTO pokemonToHighestPokemon(Pokemon pokemon);
    MoreBaseExperiencePokemonDTO pokemonToMoreBaseExperiencePokemon(Pokemon pokemon);
}
