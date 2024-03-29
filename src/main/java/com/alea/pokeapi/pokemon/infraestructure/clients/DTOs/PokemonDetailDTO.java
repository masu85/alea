package com.alea.pokeapi.pokemon.infraestructure.clients.DTOs;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PokemonDetailDTO {
    private final Integer id;
    private final String name;
    private final Integer height;
    private final Integer weight;
    private final Integer baseExperience;
}
