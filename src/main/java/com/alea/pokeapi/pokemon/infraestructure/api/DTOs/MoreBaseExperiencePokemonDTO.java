package com.alea.pokeapi.pokemon.infraestructure.api.DTOs;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MoreBaseExperiencePokemonDTO {
    private final Integer id;
    private final String name;
    private final Integer baseExperience;
}
