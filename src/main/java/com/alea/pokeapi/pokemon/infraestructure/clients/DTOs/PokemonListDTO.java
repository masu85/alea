package com.alea.pokeapi.pokemon.infraestructure.clients.DTOs;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PokemonListDTO {

    private final Integer count;
    private final String next;
    private final String previous;
    private final List<Result> results;

    @Getter
    public static class Result {
        private String name;
        private String url;
    }
}
