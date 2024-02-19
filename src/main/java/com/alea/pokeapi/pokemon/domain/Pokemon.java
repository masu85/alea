package com.alea.pokeapi.pokemon.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Pokemon {

    private final UUID internalId;
    private Integer id;
    private String name;
    private Integer height;
    private Integer weight;
}

