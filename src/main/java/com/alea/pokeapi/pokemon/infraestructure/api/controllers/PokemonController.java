package com.alea.pokeapi.pokemon.infraestructure.api.controllers;

import com.alea.pokeapi.pokemon.application.CreatePokemonUseCase;
import com.alea.pokeapi.pokemon.infraestructure.clients.PokeApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
@RequiredArgsConstructor
public class PokemonController {

    private final PokeApiClient pokeApiClient;
    private final CreatePokemonUseCase createPokemonUseCase;

    @GetMapping
    public void test() {
        System.out.println(pokeApiClient.countPokemons().block());
        System.out.println(pokeApiClient.getPokemon(1).map(createPokemonUseCase::createPokemon).block());
    }
}
