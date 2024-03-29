package com.alea.pokeapi.pokemon.infraestructure.api.DTOs;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HeaviestPokemonDTO {
    private final Integer id;
    private final String name;
    private final Integer weight;
}
