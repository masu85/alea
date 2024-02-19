package com.alea.pokeapi.pokemon.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Pokemon {
    private final String internalId;
    private Integer id;
    private String name;
    private Integer height;
    private Integer weight;
    private Integer baseExperience;
}

