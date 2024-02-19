package com.alea.pokeapi.pokemon.infraestructure.clients.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PokemonPokeApiDTO {

    private final Integer id;
    private final String name;
    private final Integer height;
    private final Integer weight;
}
